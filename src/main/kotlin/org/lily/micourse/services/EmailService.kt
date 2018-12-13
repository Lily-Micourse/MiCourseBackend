package org.lily.micourse.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

/**
 * Author: J.D. Liao
 * Date: 2018/12/12
 * Description:
 */

@Service
class EmailService {

    @Autowired
    private lateinit var mailSender: JavaMailSender

    fun sendMessage(to: String, subject: String, text: String) {
        val mail = SimpleMailMessage()
        mail.setTo(to)
        mail.setSubject(subject)
        mail.setText(text)
        mailSender.send(mail)
    }
}