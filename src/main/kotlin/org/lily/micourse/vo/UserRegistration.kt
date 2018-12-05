package org.lily.micourse.vo

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Author: J.D. Liao
 * Date: 2018/12/5
 * Description:
 */

data class UserRegistration(
    @field:Email(message = "email not valid")
    @field:NotBlank(message = "empty email")
    val email: String?,

    @field:NotBlank(message = "empty password")
    val password: String?
)