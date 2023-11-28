package daniil.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue
    val id: UUID,

    @Column
    val content: String,

    @Column
    val createdAt: OffsetDateTime,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "video_id")
    val video: Video
) {

}
