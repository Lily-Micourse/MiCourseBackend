package org.lily.micourse.services.course.impl

import org.lily.micourse.dao.course.CourseCommentDao
import org.lily.micourse.dao.course.CourseCommentVoteDao
import org.lily.micourse.dao.course.CourseInfoDao
import org.lily.micourse.dao.user.UserDao
import org.lily.micourse.exception.ResourceNotFoundException
import org.lily.micourse.po.course.CourseComment
import org.lily.micourse.po.course.CourseCommentVote
import org.lily.micourse.po.course.CourseSubComment
import org.lily.micourse.po.course.CourseSubCommentVote
import org.lily.micourse.services.course.CourseCommentService
import org.lily.micourse.vo.course.CourseCommentVO
import org.lily.micourse.vo.course.CourseCommetVotingStateVO
import org.lily.micourse.vo.course.CourseSubCommentVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
@Service
class CourseCommentServiceImpl: CourseCommentService {

    @Autowired
    private lateinit var userInfoDao: UserDao

    @Autowired
    private lateinit var courseInfoDao: CourseInfoDao

    @Autowired
    private lateinit var courseCommentDao: CourseCommentDao

    @Autowired
    private lateinit var courseCommentVoteDao: CourseCommentVoteDao

    private fun convertBooleanToNumber(boolean: Boolean?): Int = when (boolean) {
                null -> 0
                true -> 1
                false -> -1
            }

    override fun getCourseComments(courseId: Int): List<CourseCommentVO> {
        // 获取原始数据
        val rawComments = courseCommentDao.getCommentList(courseId).filter { !it.deleted }
        val rawSubComments = mutableListOf<CourseSubComment>()
        rawComments.forEach { rawSubComments.addAll(it.subComments) }

        // 获取涉及的用户的信息
        val relatedUserIds = mutableSetOf<Int>()
        relatedUserIds.addAll(rawComments.map { it.userId })
        relatedUserIds.addAll(rawSubComments.map { it.userId })

        val userMap = userInfoDao.getUsersByIds(relatedUserIds)

        //  获取评论的点赞数
        val commentVoteMap = courseCommentVoteDao.findVotingByCourseId(courseId)
                .groupBy { it.commentId }.mapValues { Pair(it.value.count { t -> t.agree },
                        it.value.count { t -> !t.agree }) }
        val subCommentVoteMap = courseCommentVoteDao.findSubVotingByCourseId(courseId)
                .groupBy { it.subCommentId }.mapValues { Pair(it.value.count { t -> t.agree },
                        it.value.count { t -> !t.agree}) }

        // 构建VO对象
        val commentVOs = rawComments.map { CourseCommentVO(it, userMap[it.userId]!!,
                commentVoteMap[it.userId]!!.first, commentVoteMap[it.userId]!!.second) }
        val subCommentVOs = rawSubComments.map { CourseSubCommentVO(it, userMap[it.userId]!!,
                subCommentVoteMap[it.userId]!!.first, subCommentVoteMap[it.userId]!!.second) }

        // 构建树状结构
        // 这他喵的也太不好办了8
        val commentVOIdMap = mutableMapOf<Int, CourseCommentVO>()
        val subCommentVOIdMap = mutableMapOf<Int, CourseSubCommentVO>()
        commentVOs.forEach { commentVOIdMap[it.id] = it }
        subCommentVOs.forEach { subCommentVOIdMap[it.id] = it }

        for (subComment: CourseSubComment in rawSubComments) {
            val courseSubCommentVO = subCommentVOIdMap[subComment.id]!!
            if (subComment.replyToSubCommentId == null) {
                val courseCommentVO = commentVOIdMap[subComment.courseComment.id]!!
                courseCommentVO.commentCourses.add(courseSubCommentVO)
            } else {
                val parentSubCommentVO = subCommentVOIdMap[subComment.replyToSubCommentId]!!
                parentSubCommentVO.courseSubComments.add(courseSubCommentVO)
            }
        }

        return commentVOs
    }

    override fun doComment(courseId: Int, userId: Int, content: String, term: String) {
        if (!courseInfoDao.existCourse(courseId)) throw ResourceNotFoundException()
        val comment = CourseComment(courseId = courseId, userId = userId, content = content,
                subComments = setOf(), semester = term)
        courseCommentDao.addComment(comment)
    }

    override fun doSubComment(userId: Int, content: String, commentId: Int, subCommentId: Int?) {
        val comment = courseCommentDao.getCommentById(commentId).orElse(null)
                ?: throw ResourceNotFoundException()
        val subComment = CourseSubComment(courseComment = comment, replyToSubCommentId = subCommentId
                , content = content, userId = userId)
        courseCommentDao.addSubComment(subComment)
    }

    override fun getCourseCommentVotingState(courseId: Int, userId: Int): CourseCommetVotingStateVO {
        val commentVotes = courseCommentVoteDao.findVotingByUserIdAndCourseId(userId, courseId)
                .stream().collect(Collectors.toMap(CourseCommentVote::id, CourseCommentVote::agree))

        val subCommentVotes = courseCommentVoteDao.findSubVotingByUserIdAndCourseId(userId, courseId)
                .stream().collect(Collectors.toMap(CourseSubCommentVote::id, CourseSubCommentVote::agree))

        val comments = courseCommentDao.getCommentList(courseId)

        val commentVoteState: MutableMap<Int, Int> = mutableMapOf()
        val subCommentVoteState: MutableMap<Int, Int> = mutableMapOf()
        comments.forEach { it ->
            run {
                commentVoteState[it.id] = convertBooleanToNumber(commentVotes[it.id])
                it.subComments.forEach {
                    it -> subCommentVoteState[it.id] = convertBooleanToNumber(subCommentVotes[it.id])
                }
            }
        }
        return CourseCommetVotingStateVO(commentVoteState, subCommentVoteState)
    }
}