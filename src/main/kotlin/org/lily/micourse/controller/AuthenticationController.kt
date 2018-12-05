package org.lily.micourse.controller

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.lily.micourse.exception.DuplicateException
import org.lily.micourse.services.UserService
import org.lily.micourse.vo.UserRegistration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * Author: J.D. Liao
 * Date: 2018/11/8
 * Description:
 */

@RestController
class AuthenticationController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/user/signup")
    @ApiOperation(value = "register a new user", notes = "verify the user and return a token")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 409, message = "Email already used")
        ]
    )
    fun register(@Valid user: UserRegistration, request: HttpServletRequest) {

        if (userService.userAlreadyExists(user.email!!))
            throw DuplicateException("Email already used : ${user.email}")

        userService.saveUser(user, request.remoteAddr)
    }

    @GetMapping("/user/login")
    fun login(username: String, password: String): String = "test"
}
