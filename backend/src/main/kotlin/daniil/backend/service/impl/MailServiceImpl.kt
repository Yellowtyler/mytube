package daniil.backend.service.impl

import daniil.backend.property.MailProperty
import daniil.backend.service.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    @Autowired private val mailSender: JavaMailSender,
    @Autowired private val mailProperty: MailProperty
): MailService {


    override fun sendMessage(to: String, subject: String, text: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, false, "windows-1251")
        helper.setTo(to)
        helper.setFrom(mailProperty.from)
        helper.setSubject(subject)
        helper.setText(text)
        mailSender.send(message)
    }
}