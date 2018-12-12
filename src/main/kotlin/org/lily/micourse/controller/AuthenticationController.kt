package org.lily.micourse.controller

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.lily.micourse.config.security.UserPrincipal
import org.lily.micourse.config.security.JwtTokenProvider
import org.lily.micourse.exception.DuplicateException
import org.lily.micourse.services.UserService
import org.lily.micourse.vo.authentication.LoginRequest
import org.lily.micourse.vo.authentication.TokenResponse
import org.lily.micourse.vo.authentication.UserRegistration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
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

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

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
    fun login(@Valid loginRequest: LoginRequest): TokenResponse {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        )

        // set security context
        SecurityContextHolder.getContext().authentication = authentication

        // generate jwt token and return to front-end
        return TokenResponse(jwtTokenProvider.generateToken(authentication), jwtTokenProvider.tokenType)
    }
}
