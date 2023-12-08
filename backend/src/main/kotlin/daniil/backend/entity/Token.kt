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

}