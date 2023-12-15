package daniil.backend.service.impl

import daniil.backend.dto.auth.*
import daniil.backend.entity.Token
import daniil.backend.entity.User
import daniil.backend.enums.TokenType
import daniil.backend.exception.*
import daniil.backend.extension.throwTokenNotFound
import daniil.backend.extension.throwUserNotFound
import daniil.backend.property.ClientProperty
import daniil.backend.repository.TokenRepository
import daniil.backend.repository.UserRepository
import daniil.backend.service.AuthService
import daniil.backend.service.MailService
import daniil.backend.service.TokenService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class AuthServiceImpl(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val tokenRepository: TokenRepository,
    @Autowired private val tokenService: TokenService,
    @Autowired private val mailService: MailService,
    @Autowired private val passwordEncoder: PasswordEncoder,
    @Autowired private val clientProperty: ClientProperty
    ): AuthService {

    private val logger = KotlinLogging.logger {  }

    override fun register(req: RegisterRequest) {
        if (req.password.length < 8)
            throw BadPasswordException("password must contain more than 7 characters")
        if (userRepository.existsByNameOrMail(req.name, req.mail)) {
            val msg = "user with $req.name or $req.mail already exists!"
            logger.error { "register() - $msg" }
            throw UserAlreadyExistsException(msg)
        }

        val saltedPassword = passwordEncoder.encode(req.password)
        val newUser = User(req.name, req.mail, saltedPassword)
        val savedUser = userRepository.save(newUser)

        val token = Token(
            null,
            UUID.randomUUID().toString(),
            TokenType.CONFIRM_REGISTRATION,
            OffsetDateTime.now().plusDays(1),
            savedUser
        )
        val savedToken = tokenRepository.save(token)

        mailService.sendMessage(
            req.mail,
            "Confirm registration",
            "please confirm via link ${clientProperty.url}/confirm-registration/${savedToken.token}"
        )
    }

    override fun login(req: LoginRequest): LoginResponse {
        val user: User = userRepository.findByName(req.name) ?: throwUserNotFound(req.name)

        if (!passwordEncoder.matches(req.password, user.password)) {
            logger.error { "login() - wrong password for ${req.name}" }
            throw WrongPasswordException("wrong password")
        }
        val token = tokenService.generateToken(user)
        user.token = token
        userRepository.save(user)
        return LoginResponse(token)
    }

    override fun logout(token: String, auth: Authentication) {
        val user: User = userRepository.findByName(auth.name) ?: throwUserNotFound(auth.name)
        user.token = null
        userRepository.save(user)
    }

    override fun resetPassword(req: ResetPasswordRequest) {
        val tokenEntity = tokenRepository.findByToken(req.token) ?: throwTokenNotFound(req.token)
        if (OffsetDateTime.now().isAfter(tokenEntity.expirationDateTime)) {
            logger.error { "resetPassword() - token ${req.token} has been expired" }
            throw ExpiredTokenException("token ${req.token} has been expired")
        }

        if (req.newPassword.length < 8)
            throw BadPasswordException("password must contain more than 7 characters")

        val user = tokenEntity.user

        val newPassword = passwordEncoder.encode(req.newPassword)
        user.setPassword(newPassword)

        userRepository.save(user)
        tokenRepository.delete(tokenEntity)
    }

    override fun confirmRegistration(token: String) {
        val tokenEntity = tokenRepository.findByToken(token) ?: throwTokenNotFound(token)
        if (OffsetDateTime.now().isAfter(tokenEntity.expirationDateTime)) {
            logger.error { "confirmRegistration() - token $token has been expired" }
            throw ExpiredTokenException("token $token has been expired")
        }
        val user = tokenEntity.user
        user.isRegistered = true
        userRepository.save(user)
        tokenRepository.delete(tokenEntity)
    }

    override fun changePassword(req: ChangePasswordRequest, auth: Authentication) {
        val user = userRepository.findByName(auth.name) ?: throwUserNotFound(auth.name)

        if (req.newPassword.length < 8)
            throw BadPasswordException("password must contain more than 7 characters")

        if (!passwordEncoder.matches(req.oldPassword, user.password)) {
            logger.error { "changePassword() - wrong password for ${user.name}" }
            throw WrongPasswordException("wrong password!")
        }

        val newPassword = passwordEncoder.encode(req.newPassword)
        user.setPassword(newPassword)

        userRepository.save(user)
    }

    override fun forgotPassword(req: ForgotPasswordRequest) {
        val user: User = userRepository.findByMail(req.mail) ?: throwUserNotFound(req.mail)
        val oldToken = tokenRepository.findByUser_Id(user.id!!)
        if (oldToken != null) {
            if (oldToken.expirationDateTime.isAfter(OffsetDateTime.now()))
                throw TokenAlreadyExistsException("token already exists! check your mail")
            tokenRepository.delete(oldToken)
        }
        val token = Token(
            null,
            UUID.randomUUID().toString(),
            TokenType.RESET_PASSWORD,
            OffsetDateTime.now().plusDays(1),
            user
        )
        val savedToken = tokenRepository.save(token)
        mailService.sendMessage(
            req.mail,
            "Reset password",
            "please follow the link ${clientProperty.url}/reset-password/${savedToken.token}"
        )
    }


}