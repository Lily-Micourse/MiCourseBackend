package org.lily.micourse.services.deprecated.course

import org.lily.micourse.entity.ResultMessage
import org.lily.micourse.entity.course.CheckInFrequency
import org.lily.micourse.entity.course.CommentVotingType
import org.lily.micourse.entity.course.CoursePressure
import org.lily.micourse.entity.course.Score
import org.lily.micourse.vo.course.CourseCommentVO
import org.springframework.stereotype.Service

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Service
interface CourseCommentService {

    /**
     * 在没有登录的情况下，获取某个课程的评论列表
     */
    fun getCourseComments(courseId: Int) : List<CourseCommentVO>

    /**
     * 在已经登录的情况下，获取某个课程的评论列表
     */
    fun getCourseComments(courseId: Int, userId: Int) : List<CourseCommentVO>

    /**
     * 对某个课程进行评论
     */
    fun doComment(courseId: Int, userId: Int, content: String, term: String) : ResultMessage

    /**
     * 对评论或者子评论进行评论
     */
    fun doSubComment(userId: Int, content: String, commentId: Int, subCommentId: Int?) : ResultMessage

    /**
     * 对评论取消点赞/踩
     */
    fun cancelCommentVoting(userId: Int, commentId: Int) : ResultMessage

    /**
     * 对子评论取消点赞/踩
     */
    fun cancelSubCommentVoting(userId: Int, commentId: Int, subCommentId: Int): ResultMessage

    /**
     * 对评论进行👍|踩
     */
    fun votingComment(userId: Int, commentId: Int, commentVotingType: CommentVotingType) : ResultMessage

    /**
     * 对子评论进行👍|踩
     */
    fun votingSubComment(userId: Int, commentId: Int, subCommentId: Int, commentVotingType: CommentVotingType) : ResultMessage

    /**
     * 对课程进行反馈
     */
    fun addFeedBack(userId: Int, courseId: Int, rate: Int, pressure: CoursePressure,
                    score: Score, examineMethods: Set<String>, checkInFrequency: CheckInFrequency,
                    content: String?, term: String): ResultMessage

}