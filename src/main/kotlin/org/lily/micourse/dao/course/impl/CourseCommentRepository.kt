package org.lily.micourse.dao.course.impl

import org.lily.micourse.po.course.CourseComment
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseCommentRepository: CrudRepository<CourseComment, Int>, JpaSpecificationExecutor<CourseComment> {

    fun findAllByCourseId(courseId: Int): List<CourseComment>

    fun findAllByCourseIdIn(courseIds: Collection<Int>): List<CourseComment>

}