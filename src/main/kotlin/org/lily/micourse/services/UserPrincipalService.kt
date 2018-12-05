package org.lily.micourse.services

import org.lily.micourse.dao.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * Author: J.D. Liao
 * Date: 2018/12/5
 * Description:
 */

@Service
class UserPrincipalService : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(p0: String?): UserDetails?{
        return null
    }
}