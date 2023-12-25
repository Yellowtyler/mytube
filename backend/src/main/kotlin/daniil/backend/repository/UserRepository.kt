package daniil.backend.repository

import daniil.backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    fun findByName(name: String): User?
    fun existsByNameOrMail(name: String, mail: String): Boolean
    fun findByMail(mail: String): User?
    fun existsByName(name: String): Boolean
    fun existsByMail(mail: String): Boolean

}