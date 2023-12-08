package daniil.backend.config

import daniil.backend.property.MailProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailConfig {

    @Bean
    fun javaMailSender(mailProperty: MailProperty): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = mailProperty.host
        mailSender.port = mailProperty.port
        mailSender.username = mailProperty.username
        mailSender.password = mailProperty.password
        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = mailProperty.protocol
        props["mail.smtp.auth"] = mailProperty.auth
        props["mail.smtp.starttls.enable"] = mailProperty.tls
        props["mail.debug"] = mailProperty.debug
        return mailSender
    }
}