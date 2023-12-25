package daniil.backend.entity

import daniil.backend.enums.TokenType
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "token")
class Token(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Column
    var token: String,

    @Enumerated
    @Column
    val type: TokenType,

    @Column
    val expirationDateTime: OffsetDateTime,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User,
    ) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Token) return false

        if (id != other.id) return false
        if (token != other.token) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + token.hashCode()
        return result
    }

}