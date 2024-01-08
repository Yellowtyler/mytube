package daniil.backend.repository

import daniil.backend.entity.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LikeRepository: JpaRepository<Like, UUID> {
    fun findByUser_IdAndVideo_Id(userId: UUID, videoId: UUID): Like?
}