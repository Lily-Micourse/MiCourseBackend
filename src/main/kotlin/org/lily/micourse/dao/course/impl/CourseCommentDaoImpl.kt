package org.lily.micourse.dao.course.impl

import org.lily.micourse.dao.course.CourseCommentDao
import org.lily.micourse.po.course.CourseComment
import org.lily.micourse.po.course.CourseSubComment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

@Repository
class CourseCommentDaoImpl: CourseCommentDao {

    @Autowired
    private lateinit var courseCommentRepository: CourseCommentRepository

    @Autowired
    private lateinit var courseSubCommentRepository: CourseSubCommentRepository

    override fun addComment(comment: CourseComment) {
        courseCommentRepository.save(comment)
    }

    override fun addSubComment(subComment: CourseSubComment) {
        courseSubCommentRepository.save(subComment)
    }
    override fun getCommentById(courseCommentId: Int) = courseCommentRepository.findById(courseCommentId)

    override fun getCommentCountByCourseIds(courseIds: Collection<Int>): Map<Int, Int> {
        val resultMap: MutableMap<Int, Int> = mutableMapOf()
        courseCommentRepository.findAllByCourseIdIn(courseIds)
                .groupBy { it.courseId }.forEach { t, u -> resultMap[t] = u.sumBy { it.subComments.size + 1 } } // 计算评论数的时候也算上自评论
        return resultMap
    }

    override fun getCommentList(courseId: Int): List<CourseComment>
            = courseCommentRepository.findAllByCourseId(courseId)
}