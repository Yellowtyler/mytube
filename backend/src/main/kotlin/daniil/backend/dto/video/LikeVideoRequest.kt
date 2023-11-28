package daniil.backend.dto.video

import java.util.UUID

data class LikeVideoRequest(val videoId: UUID, val userId: UUID, val isLike: Boolean?) {

}
