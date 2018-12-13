package org.lily.micourse.dao.user

import org.lily.micourse.po.EmailValidation
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Author: J.D. Liao
 * Date: 2018/12/12
 * Description:
 */

interface EmailValidationRepository: JpaRepository<EmailValidation, Int> {
    fun findByValidationKey(token: String)
}