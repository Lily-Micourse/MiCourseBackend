package org.lily.micourse.entity

import java.time.LocalDateTime
import javax.persistence.*

/**
 * Author: J.D. Liao
 * Date: 2018/11/8
 * Description:
 */


/**
 * User entity
 * @param[registerEmail]  email used for login
 * @param[password] user password
 * @param[addIp] the ip when user registers
 * @param[portraitUrl] user's portrait url
 * @param[username] user's nickname, not used in login
 * @param[schoolDepartmentId] department id
 * @param[gender] user's gender
 * @param[schoolEmail] user's school email
 * @param[isRegisterEmailValidated] whether user's registered email has been validated
 * @param[isSchoolEmailValidated] whether user's school email has been validated
 * @param[qqNumber] user's qq number
 * @param[banned] whether this user has been banned
 * @param[addTime] user's join time, default is [LocalDateTime.now]
 * @param[id] id of this entity, should not be assigned when initialized
 *
 */
@Entity
@Table(name = "user")
data class User(
        val registerEmail: String,

        val password: String,

        val addIp: String,

        val portraitUrl: String,

        @Column(name = "user_name")
        val username: String,

        val schoolDepartmentId: Int = 0,

        @Convert(converter = GenderConverter::class)
        val gender: Gender = Gender.UNKNOWN,

        val schoolEmail: String? = null,

        val isRegisterEmailValidated: Boolean = false,

        val isSchoolEmailValidated: Boolean = false,

        val qqNumber: String? = null,

        val banned: Boolean = false,

        val addTime: LocalDateTime = LocalDateTime.now(),

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = -1
)


enum class Gender(val value: String) {
    MALE("男"),
    FEMALE("女"),
    UNKNOWN("未知")
}

@Converter
internal class GenderConverter : AttributeConverter<Gender, String> {
    override fun convertToDatabaseColumn(p0: Gender): String {
        // there exists character set conflict between String of kotlin
        // and enum string of MySQL, so just return a numeric string so that
        // MySQL can cast it into a integer (MySQL enum is stored as integer)
        return "${p0.ordinal + 1}"
    }

    override fun convertToEntityAttribute(p0: String): Gender {
        return when (p0) {
            Gender.MALE.value -> Gender.MALE
            Gender.FEMALE.value -> Gender.FEMALE
            else -> Gender.UNKNOWN
        }
    }
}