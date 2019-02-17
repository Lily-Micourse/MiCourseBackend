package org.lily.micourse.dao.deprecated.course

import org.lily.micourse.po.course.CourseDepartment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseDepartmentRepository: JpaRepository<CourseDepartment, Int> {

    @Query("select c.name from course_department c", nativeQuery = true)
    fun getDepartmentNames(): List<String>
}