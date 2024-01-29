package daniil.backend.repository

import daniil.backend.entity.Channel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ChannelRepository: JpaRepository<Channel, UUID>, JpaSpecificationExecutor<Channel> {
    fun findByVideos_Id(id: UUID?): Channel?

    @Query("select c from Channel c join Video v on c.id = v.channel.id where v.id in :ids")
    fun findAllByVideosId(@Param("ids") ids: Collection<UUID>): List<Channel>
}