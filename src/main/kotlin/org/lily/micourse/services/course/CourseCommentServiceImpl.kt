package org.lily.micourse.services.course

import org.lily.micourse.dao.course.CourseCommentDAO
import org.lily.micourse.dao.course.CourseDAO
import org.lily.micourse.dao.course.CourseFeedbackRepository
import org.lily.micourse.dao.user.UserRepository
import org.lily.micourse.entity.ResultMessage
import org.lily.micourse.entity.course.CheckInFrequency
import org.lily.micourse.entity.course.CommentVotingType
import org.lily.micourse.entity.course.CoursePressure
import org.lily.micourse.entity.course.Score
import org.lily.micourse.po.course.CourseComment
import org.lily.micourse.po.course.CourseSubComment
import org.lily.micourse.vo.course.CourseCommentVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Service
class CourseCommentServiceImpl(

        @Autowired
        var courseDAO: CourseDAO,

        @Autowired
        var userRepository: UserRepository,

        @Autowired
        var courseCommentDAO: CourseCommentDAO

//        @Autowired
//        var courseFeedbackRepository: CourseFeedbackRepository

): CourseCommentService {

    override fun getCourseComments(courseId: Int, userId: Int): List<CourseCommentVO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doComment(courseId: Int, userId: Int, content: String, term: String): ResultMessage {
        val course = courseDAO.getCourseById(courseId).orElse(null)
        val user = userRepository.findById(userId).orElse(null)
        if (course == null)
            return ResultMessage(409, "课程不存在")
        if (user == null)
            return ResultMessage(409, "用户不存在")
        val comment = CourseComment(course = course, user = user, deleted = false, content = content, semester = term,
                subComments = setOf(), addTime = Timestamp(Date().time))
        courseCommentDAO.addComment(comment)
        return ResultMessage(200, "成功")
    }

    override fun doSubComment(userId: Int, content: String, commentId: Int, subCommentId: Int?): ResultMessage {
        val comment = courseCommentDAO.getCommentById(commentId).orElse(null)
        val user = userRepository.findById(userId).orElse(null)
        if (comment == null)
            return ResultMessage(409, "评论不存在")
        if (user == null)
            return ResultMessage(409, "用户不存在")
        val subComment = CourseSubComment(courseComment = comment, replyToSubCommentId = subCommentId,
                content = content, user = user)
        courseCommentDAO.addSubComment(subComment)
        return ResultMessage(200, "成功")
    }

    override fun cancelCommentVoting(userId: Int, commentId: Int): ResultMessage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelSubCommentVoting(userId: Int, commentId: Int, subCommentId: Int): ResultMessage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun votingComment(userId: Int, commentId: Int, commentVotingType: CommentVotingType): ResultMessage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun votingSubComment(userId: Int, commentId: Int, subCommentId: Int, commentVotingType: CommentVotingType): ResultMessage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addFeedBack(userId: Int, rate: Int, pressure: CoursePressure, score: Score, examineMethods: List<String>, checkInFrequency: CheckInFrequency, content: String?, term: String): ResultMessage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}