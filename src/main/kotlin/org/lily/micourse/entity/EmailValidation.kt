package org.lily.micourse.entity

import java.time.LocalDateTime
import javax.persistence.Entity

/**
 * Author: J.D. Liao
 * Date: 2018/11/11
 * Description:
 */

data class EmailValidation(
        val userId: Int,

        val validationKey: String,

        val expired: LocalDateTime
)


enum class ValidationType {

}
