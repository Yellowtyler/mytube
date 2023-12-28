package daniil.backend.repository

import daniil.backend.entity.Video
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VideoRepository: JpaRepository<Video, UUID>, JpaSpecificationExecutor<Video> {
}