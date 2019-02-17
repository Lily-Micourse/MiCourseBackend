package org.lily.micourse.services.course.impl

import org.lily.micourse.dao.course.CourseCommentDao
import org.lily.micourse.dao.course.CourseCommentVoteDao
import org.lily.micourse.dao.course.CourseInfoDao
import org.lily.micourse.exception.ResourceNotFoundException
import org.lily.micourse.po.course.CourseComment
import org.lily.micourse.po.course.CourseSubComment
import org.lily.micourse.services.course.CourseCommentService
import org.lily.micourse.vo.course.CourseCommentVO
import org.lily.micourse.vo.course.CourseCommetVotingStateVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
@Service
class CourseCommentServiceImpl: CourseCommentService {

    @Autowired
    private lateinit var courseInfoDao: CourseInfoDao

    @Autowired
    private lateinit var courseCommentDao: CourseCommentDao

    @Autowired
    private lateinit var courseCommentVoteDao: CourseCommentVoteDao

    override fun getCourseComments(courseId: Int): List<CourseCommentVO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun getCourseCommetVotingState(courseId: Int, userId: Int): CourseCommetVotingStateVO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}