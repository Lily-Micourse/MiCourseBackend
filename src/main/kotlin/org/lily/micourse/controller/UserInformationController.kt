package org.lily.micourse.controller

import io.swagger.annotations.*
import org.lily.micourse.config.security.UserPrincipal
import org.lily.micourse.entity.user.UserInfo
import org.lily.micourse.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

/**
 * Author: J.D. Liao
 * Date: 2018/12/13
 * Description:
 */

@RestController
class UserInformationController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/user/info")
    @ApiOperation(value = "get user information", response = UserInfo::class)
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        required = true,
        dataType = "string",
        paramType = "header"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 404, message = "user not found"),
            ApiResponse(code = 200, message = "OK")
        ]
    )
    fun getUserInformation(@ApiIgnore @AuthenticationPrincipal user: UserPrincipal) =
        userService.getUserInformation(user)
}