package org.lily.micourse.dao.course

import org.lily.micourse.entity.course.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */

interface CourseRepository: PagingAndSortingRepository<Course, Int> {

    fun findByCourseNameLike(courseName: String, pageable: Pageable): Page<Course>

    fun findByCredit(credit: Int, pageable: Pageable): Page<Course>

    @Query("select distinct credit from Course")
    fun findDistinctCredits(): List<Int>
}

interface CourseCommentRepository: JpaRepository<CourseComment, Int> {
    fun findByCourseId(courseId: Int): List<CourseComment>
}

@Deprecated(message = "必须人工参与")
interface CourseTagSetRepository: JpaRepository<CourseTagSet, Int>

interface CourseCategoryRepository: JpaRepository<CourseCategory, Int> {

    @Query("select name from CourseCategory")
    fun getCategoryNames(): List<String>

}

interface CourseDepartmentRepository: JpaRepository<CourseDepartment, Int> {

    @Query("select name from CourseDepartment")
    fun getDepartmentNames(): List<String>
}

interface CourseFeedbackRepository: JpaRepository<CourseFeedback, Int> {

    fun findByCourseId(courseId: Int): List<CourseFeedback>
}