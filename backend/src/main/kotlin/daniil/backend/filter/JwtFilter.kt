package daniil.backend.filter

import daniil.backend.entity.User
import daniil.backend.property.JwtProperty
import daniil.backend.service.TokenService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JwtFilter(
    @Autowired private val userDetailsService: UserDetailsService,
    @Autowired private val tokenService: TokenService,
    @Autowired private val jwtProperty: JwtProperty
): OncePerRequestFilter() {

    private val log = KotlinLogging.logger { }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.debug {
            "doFilterInternal() - started with url=${request.requestURI}, query=${request.queryString} and headers=${request.headerNames}"
        }

        if (jwtProperty.ignorePath.stream().noneMatch { s -> request.requestURI.contains(s) } && request.method != HttpMethod.OPTIONS.name()) {
            log.debug{
                "doFilterInternal() - validating token..."
            }
            val authHeader: String? = request.getHeader("Authorization")
            if (Objects.nonNull(authHeader)) {

                val headerVals = authHeader!!.split(" ")
                if (headerVals.size != 2) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "auth header is wrong or absent")
                }
                val authToken: String = headerVals[1]

                val claims = try {
                    getClaims(authToken)
                } catch (e: ExpiredJwtException) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "jwt token is expired!")
                    return
                }
                if (Objects.isNull(claims)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "jwt token is expired!")
                    return
                }

               val username = claims!!.getOrDefault("sub", "") as String
                if (username.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
                    val userDetails: User = userDetailsService.loadUserByUsername(username) as User
                    val storedToken: String? = userDetails.token
                    if (storedToken.isNullOrEmpty() || storedToken != authToken) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You've been logged out!")
                        return
                    }
                    val authentication: UsernamePasswordAuthenticationToken = try {
                        tokenService.getAuthenticationToken(authToken, userDetails)
                    } catch (e: ExpiredJwtException) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "jwt token is expired!")
                        return
                    }
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    log.debug{
                        "doFilterInternal() - authenticated user $username, setting security context"
                    }
                    log.debug{
                        "doFilterInternal() - token is valid"
                    }
                    SecurityContextHolder.getContext().authentication = authentication
                }
            } else {
                log.error{"doFilterInternal() - Authorization header is absent!"}
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is absent!")
                return
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun getClaims(token: String): Claims? {
        val claims: Claims = try {
            Jwts.parserBuilder()
                .setSigningKey(jwtProperty.secret.toByteArray())
                .setAllowedClockSkewSeconds(jwtProperty.expiration.toLong())
                .build()
                .parse(token)
                .body as Claims
        } catch (e: ExpiredJwtException) {
            tokenService.deleteExpiredToken(e.claims, token)
            log.warn{ "isExpired() - $e" }
            return null
        } catch (e: Exception) {
            log.warn{ "isExpired() - $e" }
            return null
        }
        return claims
    }

}