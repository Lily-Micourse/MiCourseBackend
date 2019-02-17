package org.lily.micourse.dao.course

import org.lily.micourse.po.course.CourseFeedback
import java.util.*

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
interface CourseFeedbackDao {

    fun addFeedback(courseFeedback: CourseFeedback)

    fun findFeedbacksByCourseId(courseId: Int): List<CourseFeedback>

    fun findFeedbackByCourseIdAndUserId(courseId: Int, userId: Int) : Optional<CourseFeedback>

    fun findCourseRatesByIds(ids: Collection<Int>) : Map<Int, Double>

}