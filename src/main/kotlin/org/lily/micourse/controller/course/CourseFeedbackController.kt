package org.lily.micourse.controller.course

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.lily.micourse.config.security.UserPrincipal
import org.lily.micourse.entity.course.CheckInFrequency
import org.lily.micourse.entity.course.CoursePressure
import org.lily.micourse.entity.course.ExamMethod
import org.lily.micourse.entity.course.Grade
import org.lily.micourse.exception.UnexpectedParamValueException
import org.lily.micourse.services.course.CourseCommentService
import org.lily.micourse.services.course.CourseFeedbackService
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
 * @author iznauy
 */
@RestController
class CourseFeedbackController {

    @Autowired
    private lateinit var courseFeedbackService: CourseFeedbackService

    @Autowired
    private lateinit var courseCommentService: CourseCommentService

    @GetMapping("/course/comment/voting")
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
    @ApiOperation("get voting states of comments")
    fun getCourseCommentsVotingState(@ApiIgnore @AuthenticationPrincipal user: UserPrincipal, courseId: String)
            = courseCommentService.getCourseCommentVotingState(courseId = courseId.toInt(), userId = user.id)

    @PostMapping("/course/feedback")
    @ApiImplicitParam(
            name = "Authorization",
            value = "jwt",
            required = true,
            dataType = "string",
            paramType = "header"
    )
    @ApiOperation("add course feedback")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, message = "ok"),
                ApiResponse(code = 404, message = "unexpected course id")
            ]
    )
    fun addFeedback(@ApiIgnore @AuthenticationPrincipal user: UserPrincipal,
                    @RequestParam courseId: String, @RequestParam rate: Double,
                    @RequestParam pressure: CoursePressure, @RequestParam examine: List<ExamMethod>,
                    @RequestParam grade: Grade, @RequestParam checkInTimes: CheckInFrequency,
                    @RequestParam(required = false) content: String?, @RequestParam term: String) {
        courseFeedbackService.addFeedBack(courseId.toInt(), user.id, rate, pressure, grade, examine.toSet(), checkInTimes,
                term)
        if (content != null)
            courseCommentService.doComment(courseId.toInt(), user.id, content, term)
    }

    @GetMapping("/course/feedback")
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
    fun hasFeedback(@ApiIgnore @AuthenticationPrincipal user: UserPrincipal,
                    @RequestParam courseId: String) = courseFeedbackService.hasFeedBack(courseId.toInt(),
            user.id)


    @PostMapping("/course/comment/feedback")
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
                ApiResponse(code = 404, message = "unexpected commentId/subCommentId/action"),
                ApiResponse(code = 409, message = "has voted")
            ]
    )
    fun addFeedbackToComment(@ApiIgnore @AuthenticationPrincipal user: UserPrincipal,
                             @RequestParam(required = false) commentId: String?,
                             @RequestParam(required = false) subCommentId: String?,
                             @RequestParam action: String,
                             @RequestParam(required = false) content: String?) {
        when(action) {
            "agree" -> {
                addCommentVoting(user.id, commentId, subCommentId, true)
            }
            "disagree" -> {
                addCommentVoting(user.id, commentId, subCommentId, false)
            }
            "cancelVote" -> {
                if (subCommentId != null)
                    courseFeedbackService.cancelSubCommentVoting(user.id, commentId!!.toInt(), subCommentId.toInt())
                else
                    courseFeedbackService.cancelCommentVoting(user.id, commentId!!.toInt())
            }
            "reply" -> {
                courseCommentService.doSubComment(user.id, content!!, commentId!!.toInt(), subCommentId!!.toInt())
            }
            else ->
                throw UnexpectedParamValueException()
        }
    }

    private fun addCommentVoting(userId: Int, commentId: String?, subCommentId: String?, agree: Boolean) {
        if (subCommentId != null)
            courseFeedbackService.votingSubComment(userId, commentId!!.toInt(),
                    subCommentId.toInt(), agree)
        else
            courseFeedbackService.votingComment(userId, commentId!!.toInt(),
                    agree)
    }

}