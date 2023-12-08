package daniil.backend.service

import daniil.backend.entity.User
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails

interface TokenService {
    fun generateToken(user: User): String
    fun generateToken(authentication: Authentication): String
    fun getAuthenticationToken(token: String, userDetails: UserDetails): UsernamePasswordAuthenticationToken
    fun isExpired(token: String): Boolean
    fun deleteExpiredToken(claims: Claims, token: String)
}