package org.lily.micourse.po.user

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Author: J.D. Liao
 * Date: 2018/11/8
 * Description:
 */


/**
 * User entity
 * @property[registerEmail]  email used for login
 * @property[password] user password
 * @property[addIp] the ip when user registers
 * @property[portraitUrl] user's portrait url
 * @property[username] user's nickname, not used in login
 * @property[schoolDepartmentId] department id
 * @property[gender] user's gender
 * @property[schoolEmail] user's school email
 * @property[isRegisterEmailValidated] whether user's registered email has been validated
 * @property[isSchoolEmailValidated] whether user's school email has been validated
 * @property[qqNumber] user's qq number
 * @property[banned] whether this user has been banned
 * @property[addTime] user's join time, default is [LocalDateTime.now]
 * @property[id] id of this entity, should not be assigned when initialized
 *
 */
@Entity
@Table(name = "user")
data class User(
    val registerEmail: String,

    val password: String,

    val addIp: String,

    val portraitUrl: String,

    val username: String,

    val schoolDepartmentId: Int = 0,

    @Enumerated
    val gender: Gender = Gender.UNKNOWN,

    val schoolEmail: String? = null,

    val isRegisterEmailValidated: Boolean = false,

    val isSchoolEmailValidated: Boolean = false,

    val qqNumber: String? = null,

    val banned: Boolean = false,

    val addTime: LocalDateTime = LocalDateTime.now(),

    val grade: Int = LocalDate.now().year,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = -1
)


enum class Gender(val representation: String) {
    MALE("male"),
    FEMALE("female"),
    UNKNOWN("unknown")
}

fun convertGenderFromString(gender: String): Gender {
    return when(gender) {
        Gender.MALE.representation -> Gender.MALE
        Gender.FEMALE.representation -> Gender.FEMALE
        else -> Gender.UNKNOWN
    }
}