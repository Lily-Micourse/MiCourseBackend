package org.lily.micourse.services

import org.lily.micourse.dao.user.UserRepository
import org.lily.micourse.entity.user.User
import org.lily.micourse.vo.UserRegistration
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

    fun userAlreadyExists(email: String) = userRepository.findByRegisterEmail(email) != null

    fun saveUser(userRegistration: UserRegistration, ip: String) {
        // todo decide portrait url
        val encryptedPassword = passwordEncoder.encode(userRegistration.password)
        val user = User(userRegistration.email!!, encryptedPassword, ip,
            "not known", " not known")

        userRepository.save(user)
    }
}