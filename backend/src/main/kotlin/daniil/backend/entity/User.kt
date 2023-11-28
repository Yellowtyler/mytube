package daniil.backend.entity

import daniil.backend.enums.UserRole
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Column
    @Id
    val id: UUID,

    @Column(unique = true)
    val name: String,

    @Column(unique = true)
    var mail: String,

    @Column
    var password: String,

    @Column
    val createdAt: OffsetDateTime,

    @Enumerated
    @Column
    var userRole: UserRole,

    @Column
    var token: String?,

    @OneToOne(fetch = FetchType.LAZY)
    val ownChannel: Channel,

    @OneToMany(fetch = FetchType.LAZY)
    val likes: Set<Like>

) {
}
