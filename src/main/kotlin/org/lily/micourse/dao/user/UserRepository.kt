package org.lily.micourse.dao.user

import org.lily.micourse.po.user.User
import org.springframework.data.repository.CrudRepository

/**
 * Author: J.D. Liao
 * Date: 2018/11/8
 * Description:
 */

interface UserRepository : CrudRepository<User, Int> {
    fun findByUsername(username: String): User?

    fun findByRegisterEmail(email: String): User?

    fun findAllByUserIdIn(ids: Collection<Int>): List<User>

}
