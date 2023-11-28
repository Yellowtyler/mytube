package daniil.backend.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "channel")
class Channel(
    @Id
    @Column
    val id: UUID,

    @Column
    var name: String,

    @Column
    val createdAt: OffsetDateTime,

    @Column
    var isBlocked: Boolean,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    val owner: User,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    val subscribers: Set<User>,

    @OneToMany(fetch = FetchType.LAZY)
    val videos: Set<Video>

    ) {

}
