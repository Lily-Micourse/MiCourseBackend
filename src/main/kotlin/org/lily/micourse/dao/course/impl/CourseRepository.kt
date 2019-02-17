package org.lily.micourse.dao.course.impl

import org.lily.micourse.po.course.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
interface CourseRepository: JpaRepository<Course, Int>, JpaSpecificationExecutor<Course> {

    @Query(value = "select distinct c.credit from course c", nativeQuery = true)
    fun getCreditLabels(): List<Int>

}