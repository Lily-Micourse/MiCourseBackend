package org.lily.micourse.dao.course.impl

import org.lily.micourse.po.course.CourseCategory
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseCategoryRepository: CrudRepository<CourseCategory, Int> {

    @Query(value = "select c.name from course_category c", nativeQuery = true)
    fun getCategoryNames(): List<String>

}