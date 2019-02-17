package org.lily.micourse.dao.course

import org.lily.micourse.po.course.CourseComment
import org.lily.micourse.po.course.CourseSubComment
import java.util.*

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseCommentDao {

    fun addComment(comment: CourseComment)

    fun addSubComment(subComment: CourseSubComment)

    fun getCommentById(courseCommentId: Int): Optional<CourseComment>

    fun getCommentCountByCourseIds(courseIds: Collection<Int>) : Map<Int, Int>

    fun getCommentList(courseId: Int) : List<CourseComment>

}