package org.lily.micourse.controller

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.lily.micourse.config.security.JwtTokenProvider
import org.lily.micourse.config.security.UserPrincipal
import org.lily.micourse.config.security.logger
import org.lily.micourse.entity.security.LoginRequest
import org.lily.micourse.entity.security.OnRegistrationCompleteEvent
import org.lily.micourse.entity.security.TokenResponse
import org.lily.micourse.entity.security.UserRegistration
import org.lily.micourse.entity.user.PasswordChange
import org.lily.micourse.exception.DuplicateException
import org.lily.micourse.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import springfox.documentation.annotations.ApiIgnore
import java.util.*
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

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    @PostMapping("/user")
    @ApiOperation(value = "register a new user")
    @ApiResponses(
        value = [
            ApiResponse(code = 204, message = "OK"),
            ApiResponse(code = 409, message = "Email already used")
        ]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun register(@Valid user: UserRegistration, request: HttpServletRequest) {

        if (userService.userAlreadyExists(user.email!!))
            throw DuplicateException("Email already used : ${user.email}")

        userService.saveUser(user, request.remoteAddr)
    }

    @GetMapping("/user")
    @ApiOperation(value = "login", notes = "verify the user and return a token")
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
        return TokenResponse(
            jwtTokenProvider.generateToken(authentication),
            jwtTokenProvider.tokenType
        )
    }

    @PutMapping("user/password")
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        required = true,
        dataType = "string",
        paramType = "header"
    )
    @ApiOperation(value = "modify password")
    fun changePassword(@ApiIgnore @AuthenticationPrincipal user: UserPrincipal, @Valid passwordChange: PasswordChange) {
        // Verify old password
    }

    @GetMapping("user/validation")
    @ApiOperation(value = "validate email", notes = "validate registered email(not school email)")
    fun validationEmail(username: String, request: HttpServletRequest) =
        sendRegistrationConfirmationEmail(username, request.contextPath, request.locale)

    @GetMapping("user/registrationConfirm")
    fun confirmRegistrationEmail(request: WebRequest, token: String) {

    }


    private fun sendRegistrationConfirmationEmail(email: String, appUrl: String, locale: Locale) {

        try {
            // publish an event to make handler send a email to target email account
            eventPublisher.publishEvent(OnRegistrationCompleteEvent(email, appUrl, locale))
        } catch (e: Exception) {
            logger.error("Email {} error", email)
        }
    }
}
