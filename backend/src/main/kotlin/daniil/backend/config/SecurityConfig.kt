package daniil.backend.config

import daniil.backend.entity.Channel
import daniil.backend.entity.User
import daniil.backend.filter.JwtFilter
import daniil.backend.property.ClientProperty
import daniil.backend.repository.ChannelRepository
import daniil.backend.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.OffsetDateTime
import java.util.*

@EnableWebSecurity
@Configuration
class SecurityConfig (
    @Autowired private val jwtFilter: JwtFilter,
    @Autowired private val clientProperty: ClientProperty
){


    private val log = KotlinLogging.logger {  }

    @Bean
    fun securityChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { disable() }
            csrf { disable() }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtFilter)
            authorizeRequests {
                authorize(HttpMethod.POST, "/api/auth/login", permitAll)
                authorize(HttpMethod.POST, "/api/auth/register", permitAll)
                authorize(HttpMethod.POST, "/api/auth/reset-password", permitAll)
                authorize(HttpMethod.POST, "/api/auth/change-password", authenticated)
                authorize(HttpMethod.POST, "/api/auth/logout", authenticated)
                authorize(HttpMethod.POST, "/api/auth/forgot-password", permitAll)
                authorize(HttpMethod.POST, "/api/auth/logout", authenticated)
                authorize(HttpMethod.POST, "/api/video/**", authenticated)
                authorize(HttpMethod.PATCH, "/api/video/**", permitAll)
                authorize(HttpMethod.PUT, "/api/video/**", authenticated)
                authorize(HttpMethod.GET, "/api/video/**", permitAll)
                authorize(HttpMethod.POST, "/api/like/**", authenticated)
                authorize(HttpMethod.DELETE, "/api/like/**", authenticated)
                authorize(HttpMethod.DELETE, "/api/video/**", hasRole("MODERATOR"))
                authorize(HttpMethod.POST, "/api/channel/**", authenticated)
                authorize(HttpMethod.PUT, "/api/channel", authenticated)
                authorize(HttpMethod.DELETE, "/api/channel/**", hasRole("MODERATOR"))
                authorize(HttpMethod.GET, "/api/channel/**", permitAll)
                authorize(HttpMethod.GET, "/api/user/**", authenticated)
                authorize(HttpMethod.POST, "/api/user/**", authenticated)
                authorize(HttpMethod.PATCH, "/api/user/**", authenticated)
                authorize(HttpMethod.POST, "/api/image/**", authenticated)
                authorize(HttpMethod.GET, "/api/image/**", permitAll)
                authorize(HttpMethod.OPTIONS, "/**", permitAll)
                authorize("/error", permitAll)
            }
            exceptionHandling {
                authenticationEntryPoint = AuthenticationEntryPoint { request, response, authException ->  response.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
            }
            httpBasic { disable() }
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
       return object: WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry
                    .addMapping("/**")
                    .allowedOrigins(clientProperty.url)
                    .allowedMethods("POST", "GET", "PUT", "HEAD", "DELETE", "PATCH")
            }
        }
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.getAuthenticationManager()
    }

    @Bean
    fun commandRunner(encoder: PasswordEncoder,
                      userRepository: UserRepository,
                      channelRepository: ChannelRepository
    ) : CommandLineRunner {
        return CommandLineRunner {
            run {
                log.info { "run() - executing..." }
                val newUser = User("user01", "test@gmail.com", encoder.encode("12345678"))
                val savedUser = userRepository.save(newUser)
                val newChannel = Channel(
                    null, savedUser.name + UUID.randomUUID(),
                    "type description here", null, null, OffsetDateTime.now(),
                    false, savedUser, mutableSetOf(), mutableSetOf()
                )

                val savedChannel = channelRepository.save(newChannel)
                savedUser.isRegistered = true
                savedUser.ownChannel = savedChannel

                userRepository.save(savedUser)
            }
        }
    }

}