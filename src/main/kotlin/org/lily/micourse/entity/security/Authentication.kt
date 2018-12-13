package org.lily.micourse.entity.security

import org.springframework.context.ApplicationEvent
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Author: J.D. Liao
 * Date: 2018/12/8
 * Description:
 */
/**
 * Author: J.D. Liao
 * Date: 2018/12/8
 * Description:
 */

class LoginRequest(
    @field:NotBlank(message = "username is empty")
    val username: String?,

    @field:NotBlank(message = "password is empty")
    val password: String?
)

data class UserRegistration(
    @field:Email(message = "email not valid")
    @field:NotBlank(message = "empty email")
    val email: String?,

    @field:NotBlank(message = "empty password")
    val password: String?
)

data class TokenResponse(val token: String, val tokenType: String)

class OnRegistrationCompleteEvent(val registeredEmail: String, val appUrl: String, val locale: Locale) :
    ApplicationEvent(registeredEmail)
