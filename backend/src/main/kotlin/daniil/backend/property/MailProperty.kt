package daniil.backend.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "mail")
@Component
data class MailProperty(
    var isActive: Boolean = true,
    var host: String = "",
    var port: Int = 0,
    var username: String = "",
    var password: String = "",
    var from: String = "",
    var protocol: String = "smtp",
    var auth: String = "",
    var tls: String = "",
    var debug: String = "",
)
