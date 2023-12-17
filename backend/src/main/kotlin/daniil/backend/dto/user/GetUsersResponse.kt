package daniil.backend.dto.user

data class GetUsersResponse(val list: List<UserShortDto>, val totalPages: Int, val currentPage: Int, val totalItems: Long)
