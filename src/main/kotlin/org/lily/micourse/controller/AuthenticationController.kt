package org.lily.micourse.controller

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Author: J.D. Liao
 * Date: 2018/11/8
 * Description:
 */

@RestController
class AuthenticationController {

    @GetMapping("/user/signup")
    @ApiOperation(value = "register a new user", notes = "verify the user and return a token")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "OK")
    ])
    fun register(username: String, password: String) = "test"

    @GetMapping("/user/login")
    fun login(username: String, password: String): String = "test"

}
