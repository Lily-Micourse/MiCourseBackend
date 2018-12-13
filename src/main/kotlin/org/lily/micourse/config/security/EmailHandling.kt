package org.lily.micourse.config.security

import org.lily.micourse.entity.security.OnRegistrationCompleteEvent
import org.lily.micourse.services.EmailService
import org.lily.micourse.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.net.Inet4Address
import java.util.*

/**
 * Author: J.D. Liao
 * Date: 2018/12/12
 * Description:
 */
const val VALIDATION_EMAIL_SUBJECT = "MiCourse Email Confirmation"
const val REGISTRATION_CONFIRMATION_URL = "user/registrationConfirm"

@Component
class RegistrationListener: ApplicationListener<OnRegistrationCompleteEvent> {

    @Value("\${server.port}")
    private lateinit var port: String

    private val address: String = Inet4Address.getLocalHost().hostAddress

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var messageSource: MessageSource

    @Autowired
    private lateinit var mailService: EmailService

    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        val userEmail = event.registeredEmail

        // Create and save token
        val token = UUID.randomUUID().toString()
        userService.saveEmailValidation(userEmail, token)

        // Create email messages and send it
        val confirmationUrl = "${event.appUrl}/$REGISTRATION_CONFIRMATION_URL?token=$token"
        val message = messageSource.getMessage("message.regSucc", null, event.locale)
        val text = "$message rnhttp://$address:$port$confirmationUrl"

        mailService.sendMessage(userEmail, VALIDATION_EMAIL_SUBJECT, text)
    }
}
