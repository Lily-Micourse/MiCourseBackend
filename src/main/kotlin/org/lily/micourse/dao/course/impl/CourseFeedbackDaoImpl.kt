package org.lily.micourse.dao.course.impl

import org.lily.micourse.dao.course.CourseFeedbackDao
import org.lily.micourse.po.course.CourseFeedback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
@Repository
class CourseFeedbackDaoImpl: CourseFeedbackDao {

    @Autowired
    private lateinit var courseFeedbackRepository: CourseFeedbackRepository

    override fun findFeedbacksByCourseId(courseId: Int): List<CourseFeedback>
            = courseFeedbackRepository.findAllByCourseId(courseId)

    override fun findFeedbackByCourseIdAndUserId(courseId: Int, userId: Int): Optional<CourseFeedback>
            = Optional.ofNullable(courseFeedbackRepository.findByCourseIdAndUserId(courseId, userId))


    override fun findCourseRatesByIds(ids: Collection<Int>): Map<Int, Double> {
        val result: MutableMap<Int, Double> = mutableMapOf()
        courseFeedbackRepository.findAllByCourseIdIn(ids).groupBy { it.courseId }
                .forEach { t, u -> result[t] = u.sumBy { it.rate } * 1.0 / u.size }
        return result
    }
}