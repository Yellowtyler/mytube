package daniil.backend.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "likes")
class Like(
    @Id
    @GeneratedValue
    var id: UUID?,

    @Column
    val isLike: Boolean,

    @Column
    val createdAt: OffsetDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @JoinColumn(name = "video_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val video: Video

) {

}
