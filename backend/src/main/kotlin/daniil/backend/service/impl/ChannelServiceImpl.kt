package daniil.backend.service.impl

import daniil.backend.dto.channel.*
import daniil.backend.entity.Channel
import daniil.backend.entity.User
import daniil.backend.enums.UserRole
import daniil.backend.exception.UserHasNoPermissionException
import daniil.backend.extension.getRole
import daniil.backend.extension.throwChannelNotFound
import daniil.backend.extension.throwUserNotFound
import daniil.backend.mapper.ChannelMapper
import daniil.backend.repository.ChannelRepository
import daniil.backend.repository.UserRepository
import daniil.backend.service.ChannelService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.abs

@Service
class ChannelServiceImpl(
    @Autowired private val channelRepository: ChannelRepository,
    @Autowired private val channelMapper: ChannelMapper,
    @Autowired private val userRepository: UserRepository
): ChannelService {

    val logger = KotlinLogging.logger {  }

    override fun getChannel(id: UUID): ChannelDto {
        val channel = channelRepository.findById(id).orElseThrow { throwChannelNotFound(id) }
        val dto = channelMapper.toDto(channel)
        dto.videosCount = channel.videos.size
        return dto
    }

    override fun getChannels(req: GetChannelsRequest, auth: Authentication?): GetChannelsResponse {
        if (auth == null) {
            val pageRequest = PageRequest.of(req.page, req.size)
            val channels = channelRepository.findAll(pageRequest)
            return GetChannelsResponse(
                channels.map { channelMapper.toShortDto(it) }.toList(),
                channels.totalPages, channels.number, channels.totalElements
            )
        }

        val user = userRepository.findByName(req.name) ?: throwUserNotFound(auth.name)
        val spec = Specification<Channel?> {
                channel,
                _,
                criteriaBuilder -> criteriaBuilder.`in`(channel.join<Channel, User>("subscribers").get<UUID>("id")).value(user.id)
        }
        val pageRequest = PageRequest.of(req.page, req.size)
        val subscribedChannels = channelRepository.findAll(spec, pageRequest)
        if (subscribedChannels.isEmpty || subscribedChannels.size < req.size) {
            
            val pageRequestForUnsubscribedChannels = PageRequest.of(
                req.page,
                abs(req.size - subscribedChannels.size)
            )
            val randomChannels: Page<Channel> = channelRepository.findAll(pageRequestForUnsubscribedChannels)
            return GetChannelsResponse(
                subscribedChannels.toList().plus(randomChannels.toList()).map { channelMapper.toShortDto(it) },
                randomChannels.totalPages, randomChannels.number, randomChannels.totalElements
            )
        }
        
        return GetChannelsResponse(
            subscribedChannels.toList().map { channelMapper.toShortDto(it) },
            subscribedChannels.totalPages, subscribedChannels.number, subscribedChannels.totalElements
        )

    }

    override fun subscribe(req: SubscribeChannelRequest, auth: Authentication) {
        val user = userRepository.findByName(auth.name) ?: throwUserNotFound(auth.name)
        val channel = channelRepository.findById(req.channelId).orElseThrow { throwChannelNotFound(req.channelId) }
        if (user == channel.owner) {
            throw Exception("user ${auth.name} can't subscribe to their own channel")
        }
        val subscribers = channel.subscribers
        subscribers.add(user)
        channelRepository.save(channel)
    }

    override fun blockChannel(req: BlockChannelRequest, auth: Authentication): ChannelDto {
        val role = getRole(auth)
        if (role != UserRole.MODERATOR)
            throw UserHasNoPermissionException("user ${auth.name} doesn't have permission to this resource")
        val channel = channelRepository.findById(req.channelId).orElseThrow { throwChannelNotFound(req.channelId) }
        channel.isBlocked = true
        return channelMapper.toDto(channelRepository.save(channel))
    }

    override fun deleteChannel(id: UUID, auth: Authentication) {
        val role = getRole(auth)
        if (role != UserRole.MODERATOR)
            throw UserHasNoPermissionException("user ${auth.name} doesn't have permission to this resource")
        val channel = channelRepository.findById(id).orElseThrow { throwChannelNotFound(id) }
        channelRepository.delete(channel)
    }

    override fun editChannel(req: EditChannelRequest, auth: Authentication): ChannelDto {
        val user = userRepository.findByName(auth.name) ?: throwUserNotFound(auth.name)
        val channel = channelRepository.findById(req.id).orElseThrow { throwChannelNotFound(req.id) }
        if (channel.owner != user) {
            throw UserHasNoPermissionException("user ${auth.name} doesn't own this channel")
        }
        channel.name = req.name
        channel.description = req.description
        return channelMapper.toDto(channelRepository.save(channel))
    }

}
