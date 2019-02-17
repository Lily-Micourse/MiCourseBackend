package org.lily.micourse.dao.course.impl

import org.lily.micourse.po.course.CourseFeedback
import org.springframework.data.repository.CrudRepository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseFeedbackRepository: CrudRepository<CourseFeedback, Int> {

    fun findAllByCourseId(courseId: Int): List<CourseFeedback>

    fun findByCourseIdAndUserId(courseId: Int, userId: Int): CourseFeedback?

    fun findAllByCourseIdIn(ids: Collection<Int>): List<CourseFeedback>
}