package daniil.backend.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtProperty(
    var uri: String = "",
    var header: String = "",
    var prefix: String = "",
    var expiration: Int = 0,
    var secret: String = "",
    var ignorePath: List<String> = mutableListOf()
)
