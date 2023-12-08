package daniil.backend.logging

import daniil.backend.dto.auth.ChangePasswordRequest
import daniil.backend.dto.auth.LoginRequest
import daniil.backend.dto.auth.RegisterRequest
import daniil.backend.dto.auth.ResetPasswordRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Component
@Aspect
class Logger {

    private val logger = KotlinLogging.logger {  }

    @Before("execution(* daniil.backend.service..*.*(..)))")
    fun logBefore(point: JoinPoint) {
        val args = mapArgs(point.args)
        logger.info { "${point.target.javaClass}.${point.signature.name}() - started to process $args" }
    }

    @After("execution(* daniil.backend.service..*.*(..))")
    fun logAfter(point: JoinPoint) {
        val args = mapArgs(point.args)
        logger.info { "${point.target.javaClass}.${point.signature.name}() - ended to process ${args}" }
    }

    private fun mapArgs(args: Array<Any>): List<String> {
        val mappedArgs = args.map {
            when (it) {
                is LoginRequest -> "name=${it.name}"
                is RegisterRequest -> "name=${it.name},mail=${it.mail}"
                is ChangePasswordRequest -> "name=${it.name}"
                is ResetPasswordRequest -> "token=${it.token}"
                else -> it
            }
        }
        return mappedArgs.map { it.toString() }
    }

}