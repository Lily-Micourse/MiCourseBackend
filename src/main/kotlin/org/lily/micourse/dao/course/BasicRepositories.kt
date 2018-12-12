package org.lily.micourse.dao.course

import org.lily.micourse.entity.course.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseRepository: JpaRepository<Course, Int>, JpaSpecificationExecutor<Course> {

    @Query(value = "select distinct c.credit from course c", nativeQuery = true)
    fun getCreditLabels(): List<Int>

}

@Repository
interface CourseCategoryRepository: JpaRepository<CourseCategory, Int> {

    @Query(value = "select c.name from course_category c", nativeQuery = true)
    fun getCategoryNames(): List<String>

}

@Repository
interface CourseDepartmentRepository: JpaRepository<CourseDepartment, Int> {

    @Query("select c.name from course_department c", nativeQuery = true)
    fun getDepartmentNames(): List<String>
}

@Repository
interface CourseFeedbackRepository: JpaRepository<CourseFeedbackRepository, Int> {

    fun findByCourseId(courseId: Int): List<CourseFeedback>

}

@Repository
interface CourseTermRepository: JpaRepository<CourseTerm, Int>