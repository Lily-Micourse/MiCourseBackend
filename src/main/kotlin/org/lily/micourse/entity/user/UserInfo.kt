package org.lily.micourse.entity.user

import javax.validation.constraints.Min

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

    val major: String,

    val avatar: String
)

/**
 * Modification information of a user
 */
data class UserChangeInfo(
    val nickname: String?,

    val department: String?,

    @field:Min(value = 0, message = "grade should not be less than zero")
    val grade: Int?,

    val gender: String?,

    val qq: String?,

    val major: String?
)