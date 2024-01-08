package daniil.backend.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "video")
class Video(
    @Id
    @Column
    var id: UUID?,

    @Column
    var name: String,

    @Column
    var views: Long,

    @Column
    var isBlocked: Boolean,

    @Column
    var isHidden: Boolean,

    @Column
    var videoPath: String,

    @Column
    var description: String,

    @Column
    var createdAt: OffsetDateTime,

    @OneToMany(fetch = FetchType.LAZY)
    val comments: Set<Comment> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY)
    val likes: Set<Like> = mutableSetOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    val channel: Channel

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Video) return false

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
