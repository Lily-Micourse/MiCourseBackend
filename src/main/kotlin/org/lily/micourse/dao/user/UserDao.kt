package org.lily.micourse.dao.user

import org.lily.micourse.dao.SchoolDepartmentRepository
import org.lily.micourse.exception.ResourceNotFoundException
import org.lily.micourse.exception.userNotFoundException
import org.lily.micourse.po.EmailValidation
import org.lily.micourse.po.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Author: J.D. Liao
 * Date: 2018/12/13
 * Description:
 */

@Repository
class UserDao {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var schoolDepartmentRepository: SchoolDepartmentRepository

    @Autowired
    private lateinit var emailValidationRepository: EmailValidationRepository

    fun getUserDepartment(user: User) = schoolDepartmentRepository.getDepartmentName(user.schoolDepartmentId)

    fun getUser(registeredEmail: String) = userRepository.findByRegisterEmail(registeredEmail)

    fun getUser(id: Int): User? = userRepository.findById(id).get()

    fun saveUser(user: User) = userRepository.save(user)

    fun addEmailValidation(validation: EmailValidation) = emailValidationRepository.save(validation)
}