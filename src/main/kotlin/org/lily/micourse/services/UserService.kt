package org.lily.micourse.services

import org.lily.micourse.config.security.UserPrincipal
import org.lily.micourse.config.security.logger
import org.lily.micourse.dao.user.UserDao
import org.lily.micourse.entity.security.UserRegistration
import org.lily.micourse.entity.user.PasswordChange
import org.lily.micourse.entity.user.UserChangeInfo
import org.lily.micourse.entity.user.UserInfo
import org.lily.micourse.exception.OldPasswordException
import org.lily.micourse.exception.userNotFoundException
import org.lily.micourse.po.EmailValidation
import org.lily.micourse.po.ValidationType
import org.lily.micourse.po.user.User
import org.lily.micourse.po.user.convertGenderFromString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Author: J.D. Liao
 * Date: 2018/12/5
 * Description:
 */

const val UNKNOWN_FIELD = "未知"

@Service
class UserService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    @Autowired
    private lateinit var userDao: UserDao

    fun userAlreadyExists(email: String) = userDao.getUser(email) != null

    /**
     * Add new user to database
     *
     * @param userRegistration user registration information
     * @param ip user's remote ip address
     */
    fun saveUser(userRegistration: UserRegistration, ip: String): User {
        // todo decide portrait url
        val portraitAddress = "tempt.jpg"
        val encryptedPassword = passwordEncoder.encode(userRegistration.password)
        val user = User(
            userRegistration.email!!, encryptedPassword, ip,
            portraitAddress, "not known"
        )

        return userDao.saveUser(user)
    }

    /**
     * Save a email validation token
     * @param registeredEmail user's registered email
     * @param token token of this validation
     * @param type validation type, default is [ValidationType.REGISTER]
     */
    fun saveEmailValidation(registeredEmail: String, token: String, type: ValidationType = ValidationType.REGISTER) {
        // Get user and his id
        val user = userDao.getUser(registeredEmail)
        val userId = user!!.id

        // Save token
        val validationToken = EmailValidation(userId, token, type)
        userDao.addEmailValidation(validationToken)
    }

    fun getUserInformation(user: UserPrincipal): UserInfo {
        // Get user's information from dao
        val userEntity = userDao.getUser(user.id) ?: throw userNotFoundException(user.username)
        val userDepartment = userDao.getUserDepartment(userEntity) ?: UNKNOWN_FIELD
        val qq = userEntity.qqNumber ?: UNKNOWN_FIELD

        // Transform to [UserInfo] object
        return UserInfo(
            userEntity.username,
            userDepartment,
            userEntity.grade,
            userEntity.gender.representation,
            qq,
            UNKNOWN_FIELD, // todo major undecided
            userEntity.portraitUrl
        )
    }

    fun modifyUserInformation(user: UserPrincipal, changeInfo: UserChangeInfo) {
        val userEntity = getUserEntity(user)

        // Get new fields of user
        val gender = changeInfo.gender?.let { convertGenderFromString(it) } ?: userEntity.gender
        val department = changeInfo.department?.let { userDao.convertUserDepartment(it) }
            ?: userEntity.schoolDepartmentId
        val grade = changeInfo.grade ?: userEntity.grade
        val qq = changeInfo.qq ?: userEntity.qqNumber
        val nickname = changeInfo.nickname ?: userEntity.username  // nickname is username in User class
        // todo consider major here

        // Create new user entity and save it
        val newUser = userEntity.copy(
            gender = gender,
            schoolDepartmentId = department,
            username = nickname,
            qqNumber = qq,
            grade = grade
        )

        userDao.saveUser(newUser)
    }

    fun modifyPassword(user: UserPrincipal, passwordChange: PasswordChange) {
        // Verify old password
        if (passwordEncoder.encode(passwordChange.oldPassword) != user.password) {
            logger.error("Old password is wrong")
            throw OldPasswordException()
        }

        // Encode new password and save it
        val encryptedNewPassword = passwordEncoder.encode(passwordChange.newPassword)
        val userEntity = getUserEntity(user)

        val newUser = userEntity.copy(password = encryptedNewPassword)

        userDao.saveUser(newUser)
    }

    private fun getUserEntity(user: UserPrincipal) = userDao.getUser(user.id) ?: throw userNotFoundException(user.username)
}