package daniil.backend.repository

import daniil.backend.entity.Channel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ChannelRepository: JpaRepository<Channel, UUID>, JpaSpecificationExecutor<Channel> {
    fun findByVideos_Id(id: UUID?): Channel?

    @Query("select c from channel c join video v on c.id = v.channel_id where v.id in ?1", nativeQuery = true)
    fun findAllByVideos_Id(ids: List<UUID>): List<Channel>
}