package daniil.backend.dto.video

class GetVideosResponse(val list: List<VideoShortDto>, val totalPages: Int, val currentPage: Int, val totalItems: Long)
