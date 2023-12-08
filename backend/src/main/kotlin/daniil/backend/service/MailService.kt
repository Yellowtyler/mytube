package daniil.backend.service

interface MailService {
    fun sendMessage(to: String, subject: String, text: String)
}