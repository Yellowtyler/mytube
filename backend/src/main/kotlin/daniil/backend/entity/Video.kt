package daniil.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
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

    @OneToMany(fetch = FetchType.LAZY)
    val comments: Set<Comment>,

    @OneToMany(fetch = FetchType.LAZY)
    val likes: Set<Like>

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
