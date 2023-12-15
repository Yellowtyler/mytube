package daniil.backend.repository

import daniil.backend.entity.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TokenRepository: JpaRepository<Token, Long> {
    fun findByToken(token: String): Token?
    fun findByUser_Id(userId: UUID): Token?
}