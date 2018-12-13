package org.lily.micourse.services

import org.lily.micourse.config.security.UserPrincipal
import org.lily.micourse.dao.user.UserDao
import org.lily.micourse.entity.security.UserRegistration
import org.lily.micourse.entity.user.UserInfo
import org.lily.micourse.exception.userNotFoundException
import org.lily.micourse.po.EmailValidation
import org.lily.micourse.po.ValidationType
import org.lily.micourse.po.user.User
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
            userEntity.gender.repr,
            qq,
            UNKNOWN_FIELD // todo major undecided
        )
    }
}