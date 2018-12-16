package org.lily.micourse.controller

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.lily.micourse.config.security.UserPrincipal
import org.lily.micourse.entity.user.UserChangeInfo
import org.lily.micourse.entity.user.UserInfo
import org.lily.micourse.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import springfox.documentation.annotations.ApiIgnore
import java.io.File
import javax.validation.Valid

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

    @PutMapping("/user/info")
    @ApiOperation(value = "modify user information")
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        required = true,
        dataType = "string",
        paramType = "header"
    )
    @ApiResponse(code = 404, message = "user not found")
    fun changeUserInformation(@ApiIgnore @AuthenticationPrincipal user: UserPrincipal, @Valid changeInfo: UserChangeInfo) {
        userService.modifyUserInformation(user, changeInfo)
    }

    @PutMapping("/user/avatar")
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        required = true,
        dataType = "string",
        paramType = "header"
    )
    @ApiOperation(value = "change user's avatar")
    fun updateAvatar(@ApiIgnore @AuthenticationPrincipal user: UserPrincipal, avatar: MultipartFile) {
        userService.storeAvatar(user, avatar)
    }

}