package org.lily.micourse.services.deprecated.course

import org.lily.micourse.dao.deprecated.course.CourseCommentDAO
import org.lily.micourse.dao.deprecated.course.CourseDAO
import org.lily.micourse.dao.deprecated.course.CourseFeedbackRepository
import org.lily.micourse.dao.deprecated.course.CourseVoteDao
import org.lily.micourse.dao.user.UserRepository
import org.lily.micourse.entity.ResultMessage
import org.lily.micourse.entity.course.CheckInFrequency
import org.lily.micourse.entity.course.CommentVotingType
import org.lily.micourse.entity.course.CoursePressure
import org.lily.micourse.entity.course.Grade
import org.lily.micourse.po.course.CourseComment
import org.lily.micourse.po.course.CourseFeedback
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
class CourseCommentServiceImpl : CourseCommentService {

    @Autowired
    lateinit var courseDAO: CourseDAO

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var courseCommentDAO: CourseCommentDAO

    @Autowired
    lateinit var courseVoteDAO: CourseVoteDao

    @Autowired
    lateinit var courseFeedbackRepository: CourseFeedbackRepository

    private fun getOriginalCourseComment(courseId: Int): List<CourseComment> =
            courseCommentDAO.getCommentList(courseId)

    private fun getSubComments(comments: List<CourseComment>) : Set<CourseSubComment> =
        comments.map { it.subComments }.reduceRight { set, acc -> acc.plus(set) }

    private fun buildSortedCommentTrees(courseCommentVoteMap: Map<CourseComment, Int>,
                                 subCourseCommentVoteMap: Map<CourseSubComment, Int>): List<CourseCommentVO> {
        var resultList: MutableList<CourseCommentVO> = mutableListOf()
        courseCommentVoteMap.forEach { entry ->  {

        }}
        TODO("not implemented")
    }

    override fun getCourseComments(courseId: Int): List<CourseCommentVO> {
        val originalCourseComments = getOriginalCourseComment(courseId)
        val courseCommentVoteMap = originalCourseComments.map { Pair(it, 0) }.toMap()
        val subCourseCommentVoteMap = getSubComments(originalCourseComments).
                map { Pair(it, 0) }.toMap()
        return buildSortedCommentTrees(courseCommentVoteMap, subCourseCommentVoteMap)
    }

    override fun getCourseComments(courseId: Int, userId: Int): List<CourseCommentVO> {
        val originalCourseComments = getOriginalCourseComment(courseId)
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
        courseVoteDAO.deleteCommentVotingRecord(userId, commentId)
        //val courseCommentVote: CourseCommentVote? = courseVoteDAO.
        return ResultMessage(200, "成功")
    }

    override fun cancelSubCommentVoting(userId: Int, commentId: Int, subCommentId: Int): ResultMessage {
        courseVoteDAO.deleteSubCommentVotingRecord(userId, subCommentId)
        return ResultMessage(200, "成功")
    }

    override fun votingComment(userId: Int, commentId: Int, commentVotingType: CommentVotingType) = when (commentVotingType) {
            CommentVotingType.DISAGREE -> cancelCommentVoting(userId, commentId)
            CommentVotingType.AGREE -> {
                val commentVote = courseVoteDAO.getVotingRecordByUserIdAndCommentId(userId, commentId)
                commentVote?.let {
                    courseVoteDAO.deleteCommentVotingRecord(it)
                }
                if (commentVote == null)  ResultMessage(409, "该评论尚未点赞") else ResultMessage(200, "OK")
            }
        }

    override fun votingSubComment(userId: Int, commentId: Int, subCommentId: Int, commentVotingType: CommentVotingType) = when(commentVotingType) {
        CommentVotingType.DISAGREE -> cancelCommentVoting(userId, commentId)
        CommentVotingType.AGREE -> {
            val subCommentVote = courseVoteDAO.getSubVotingRecordByUserIdAndSubCommentId(userId, subCommentId)
            subCommentVote?.let {
                courseVoteDAO.deleteSubCommentVotingRecord(it)
            }
            if (subCommentVote == null) ResultMessage(409, "该子评论尚未点赞") else ResultMessage(200, "OK")
        }

    }

    override fun addFeedBack(userId: Int, courseId: Int, rate: Int, pressure: CoursePressure, grade: Grade,
                             examineMethods: Set<String>, checkInFrequency: CheckInFrequency, content: String?,
                             term: String): ResultMessage {
        val course = courseDAO.getCourseById(courseId).orElse(null)
        val user = userRepository.findById(userId).orElse(null)
        if (course == null)
            return ResultMessage(409, "课程不存在")
        if (user == null)
            return ResultMessage(409, "用户不存在")
        content?.let {
            val resultMessage: ResultMessage = doComment(courseId, userId, it, term)
            if (resultMessage.code != 200)
                return resultMessage
        }
        val courseFeedback = CourseFeedback(course = course, user = user,
                rate = rate, pressure = pressure, grade = grade, evalPaper =
        examineMethods.contains("paper"), evalAttendance = examineMethods.contains("attendance"),
                evalTeamWork =  examineMethods.contains("teamwork"), evalClosedBookExam =
        examineMethods.contains("closeBookExam"), evalOpenBookExam = examineMethods.contains("openBookExam"),
                evalOthers = examineMethods.contains("others"), checkInFrequency = checkInFrequency)
        courseFeedbackRepository.save(courseFeedback)
        return ResultMessage(200, "OK")
    }
}