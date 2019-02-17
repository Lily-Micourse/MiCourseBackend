package org.lily.micourse.controller.course;

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.lily.micourse.config.security.UserPrincipal
import org.lily.micourse.services.course.CourseCommentService
import org.lily.micourse.vo.course.CourseCommentVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

/**
 * Created on 17/02/2019.
 * Description:
 *
 * @author iznauy
 */

@RestController
class CourseCommentController {

    @Autowired
    private lateinit var courseCommentService: CourseCommentService

    @GetMapping("/course/comment")
    @ApiImplicitParam(
            name = "Authorization",
            value = "jwt",
            required = true,
            dataType = "string",
            paramType = "header"
    )
    @ApiOperation("get course comments by course id")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, message = "ok"),
                ApiResponse(code = 404, message = "unexpected course id")
            ]
    )
    fun getCourseComments(@RequestParam courseId: String): List<CourseCommentVO> = courseCommentService.getCourseComments(courseId = courseId.toInt())

    @PostMapping("/course/comment")
    @ApiImplicitParam(
            name = "Authorization",
            value = "jwt",
            required = true,
            dataType = "string",
            paramType = "header"
    )
    @ApiResponses(
            value = [
                ApiResponse(code = 200, message = "ok"),
                ApiResponse(code = 404, message = "unexpected course id")
            ]
    )
    @ApiOperation("add comment to course")
    fun addComment(@ApiIgnore @AuthenticationPrincipal user: UserPrincipal,
                   @RequestParam courseId: String, @RequestParam content: String,
                   @RequestParam term: String) {

    }

}
