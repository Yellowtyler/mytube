package daniil.backend.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "channel")
class Channel(
    @Id
    @GeneratedValue
    @Column
    var id: UUID?,

    @Column
    var name: String,

    @Column
    var description: String,

    @Column
    var profilePhoto: String? = null,

    @Column
    var backgroundPhoto: String? = null,

    @Column
    val createdAt: OffsetDateTime,

    @Column
    var isBlocked: Boolean,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    val owner: User,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    val subscribers: MutableSet<User> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY)
    val videos: MutableSet<Video> = mutableSetOf()

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Channel) return false

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        return result
    }

}
