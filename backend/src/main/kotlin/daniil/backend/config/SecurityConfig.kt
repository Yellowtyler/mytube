package daniil.backend.config

import daniil.backend.filter.JwtFilter
import daniil.backend.property.ClientProperty
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
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

@EnableWebSecurity
@Configuration
class SecurityConfig (
    @Autowired
    private val jwtFilter: JwtFilter,
   @Autowired
    private val clientProperty: ClientProperty
){

    @Bean
    fun securityChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { disable() }
            csrf { disable() }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeRequests {
                authorize("/api/auth/login", permitAll)
                authorize("/api/auth/register", permitAll)
                authorize("/api/auth/reset-password", permitAll)
                authorize("/api/auth/change-password", authenticated)
                authorize("/api/auth/forgot-password", permitAll)
                authorize("/api/auth/logout", authenticated)
                authorize("/api/video/**", authenticated)
                authorize("/api/user/**", authenticated)
                authorize("/api/channel/**", authenticated)
                authorize(HttpMethod.OPTIONS, "/**", permitAll)
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtFilter)
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
                registry.addMapping("/**").allowedOrigins("http://localhost:3000", clientProperty.url)
            }
        }
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.getAuthenticationManager()
    }

}