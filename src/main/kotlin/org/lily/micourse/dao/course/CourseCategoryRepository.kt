package org.lily.micourse.dao.course

import org.lily.micourse.entity.course.CourseCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseCategoryRepository: JpaRepository<CourseCategory, Int> {

    @Query(value = "select c.name from course_category c", nativeQuery = true)
    fun getCategoryNames(): List<String>

}