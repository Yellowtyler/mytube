package daniil.backend.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "likes")
class Like(
    @Id
    @GeneratedValue
    var id: UUID? = null,

    @Column
    var isLike: Boolean,

    @Column
    val createdAt: OffsetDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @JoinColumn(name = "video_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val video: Video

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Like) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}
