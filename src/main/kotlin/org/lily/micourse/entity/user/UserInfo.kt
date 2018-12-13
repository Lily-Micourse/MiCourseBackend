package org.lily.micourse.entity.user

import org.lily.micourse.config.security.UserPrincipal
import org.lily.micourse.po.user.User

/**
 * Author: J.D. Liao
 * Date: 2018/12/13
 * Description:
 */

/**
 * User information view object
 *
 * @property nickname user po's username
 * @property department user's department name
 * @property grade user's grade
 * @property major user's major
 * @property gender user's gender
 * @property qq user's qq number
 */
data class UserInfo(
    val nickname: String,

    val department: String,

    val grade: Int,

    val gender: String,

    val qq: String,

    val major: String
)