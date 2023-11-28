package daniil.backend.dto.video

import java.util.UUID

data class UploadVideoResponse(val id: UUID, val name: String, val path: String){

}
