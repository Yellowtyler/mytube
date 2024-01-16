package daniil.backend.logging

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class LoggingRequestFilter: OncePerRequestFilter() {

    private val log = KotlinLogging.logger {  }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.debug { "==========request begin===============" }
        log.debug { "method=${request.method}" }
        log.debug { "URI=${request.requestURI}" }
        log.debug { "headers=${request.getHeader(HttpHeaders.CONTENT_TYPE)}" }
        log.debug { "==========request ended===============" }
        request.parameterNames
        filterChain.doFilter(request, response)
    }

}