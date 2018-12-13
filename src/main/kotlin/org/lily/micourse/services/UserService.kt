package org.lily.micourse.services

import org.lily.micourse.dao.user.EmailValidationRepository
import org.lily.micourse.dao.user.UserRepository
import org.lily.micourse.entity.security.UserRegistration
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

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var emailValidationRepository: EmailValidationRepository

    fun userAlreadyExists(email: String) = userRepository.findByRegisterEmail(email) != null

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

        return userRepository.save(user)
    }

    /**
     * Save a email validation token
     * @param registeredEmail user's registered email
     * @param token token of this validation
     * @param type validation type, default is [ValidationType.REGISTER]
     */
    fun saveEmailValidation(registeredEmail: String, token: String, type: ValidationType = ValidationType.REGISTER) {
        // Get user and his id
        val user = userRepository.findByRegisterEmail(registeredEmail)
        val userId = user!!.id

        // Save token
        val validationToken = EmailValidation(userId, token, type)
        emailValidationRepository.save(validationToken)
    }
}