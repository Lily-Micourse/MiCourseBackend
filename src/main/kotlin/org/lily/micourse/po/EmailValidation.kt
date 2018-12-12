package org.lily.micourse.po

import java.time.LocalDateTime
import javax.persistence.*

/**
 * Author: J.D. Liao
 * Date: 2018/11/11
 * Description:
 */

@Entity
data class EmailValidation(
    val userId: Int,

    val validationKey: String,

    @Enumerated(value = EnumType.ORDINAL)
    val validationType: ValidationType,

    val expired: LocalDateTime,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = -1
)

enum class ValidationType {
    /**
     * Register email validation
     */
    REGISTER,
    /**
     * School email validation
     */
    SCHOOL_EMAIL
}