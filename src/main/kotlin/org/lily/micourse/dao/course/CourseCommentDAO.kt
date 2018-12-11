package org.lily.micourse.dao.course

import org.lily.micourse.po.course.Course
import org.lily.micourse.po.course.CourseComment
import org.lily.micourse.po.course.CourseSubComment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
class CourseCommentDAO (

        @Autowired
        var courseCommentRepository: CourseCommentRepository,

        @Autowired
        var courseSubCommentRepository: CourseSubCommentRepository

) {

    fun addComment(comment: CourseComment) = courseCommentRepository.save(comment)

    fun addSubComment(subComment: CourseSubComment) = courseSubCommentRepository.save(subComment)

    fun getCommentById(courseCommentId: Int)
            = courseCommentRepository.findById(courseCommentId)

    fun getCommentList(courseId: Int) : List<CourseComment> {
        return courseCommentRepository.findAll { p0, p1, p2 ->
            p1.where(p2.equal(p0.get<Course>("course").get<Int>("id"), courseId))
            null
        }
    }

    private fun getCommentByCommentId(id: Int)
            = courseCommentRepository.findById(id)

}