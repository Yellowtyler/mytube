package daniil.backend.service.impl

import daniil.backend.entity.User
import daniil.backend.exception.UserNotFoundException
import daniil.backend.property.JwtProperty
import daniil.backend.repository.UserRepository
import daniil.backend.service.TokenService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

@Suppress("NAME_SHADOWING")
@Service
class JwtService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val jwtProperty: JwtProperty
): TokenService {

    private val log = KotlinLogging.logger {  }

    override fun generateToken(user: User): String {
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .setSubject(user.name)
            .claim("role", user.role)
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + jwtProperty.expiration * 1000L))
            .signWith(Keys.hmacShaKeyFor(jwtProperty.secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    override fun generateToken(authentication: Authentication): String {
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .claim("role", authentication
                .authorities
                .stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .collect(Collectors.toList())
            )
            .claim("name", authentication.name)
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + jwtProperty.expiration * 1000L))
            .signWith(Keys.hmacShaKeyFor(jwtProperty.secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    override fun getAuthenticationToken(token: String, userDetails: UserDetails): UsernamePasswordAuthenticationToken {
        val jwtParser =
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtProperty.secret.toByteArray())).build()
        val claimsJws: Jws<Claims> = try {
            jwtParser.parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            deleteExpiredToken(e.claims, token)
            throw e
        }
        val claims = claimsJws.body
        val role = claims["role"].toString().replace("[", "").replace("]", "")
        val authorities: Collection<GrantedAuthority> = Stream.of(role)
            .map { role: String? ->
                SimpleGrantedAuthority(
                    role
                )
            }
            .collect(Collectors.toList())
        return UsernamePasswordAuthenticationToken(userDetails, userDetails.password, authorities)
    }

    override fun isExpired(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtProperty.secret.toByteArray())
                .setAllowedClockSkewSeconds(jwtProperty.expiration.toLong())
                .build()
                .parse(token)
                .body
        } catch (e: Exception) {
            log.error{"isExpired() - ${e.message}"}
            return true
        }
        return false
    }

    override fun deleteExpiredToken(claims: Claims, token: String) {
        val username = claims.getOrDefault("sub", "") as String
        val user: User = userRepository.findByName(username)
            ?: throw UserNotFoundException("user $username wasn't found")
        user.token = null
        userRepository.save(user)
    }

}