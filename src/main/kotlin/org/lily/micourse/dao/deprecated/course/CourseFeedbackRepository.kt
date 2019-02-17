package org.lily.micourse.dao.deprecated.course

import org.lily.micourse.po.course.CourseFeedback
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseFeedbackRepository: JpaRepository<CourseFeedback, Int> {

    fun findAllByCourseId(courseId: Int): List<CourseFeedback>

    @Query(value = "") // TODO : 查询语句撰写
    fun getCourseRatesByIds(ids: Collection<Int>) : List<Pair<Int, Double>>

}
