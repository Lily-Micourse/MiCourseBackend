package org.lily.micourse.dao

import org.lily.micourse.entity.User
import org.springframework.data.repository.CrudRepository

/**
 * Author: J.D. Liao
 * Date: 2018/11/8
 * Description:
 */

interface UserRepository : CrudRepository<User, Int> {
    fun findByUsername(username: String): User?
}