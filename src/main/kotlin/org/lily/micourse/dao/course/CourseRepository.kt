package org.lily.micourse.dao.course

import org.lily.micourse.entity.course.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseRepository: JpaRepository<Course, Int>, JpaSpecificationExecutor<Course> {

    @Query(value = "select distinct c.credit from course c", nativeQuery = true)
    fun getCreditLabels(): List<Int>

}