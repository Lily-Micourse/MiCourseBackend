package org.lily.micourse.dao.course.impl

import org.lily.micourse.po.course.CourseDepartment
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
interface CourseDepartmentRepository: CrudRepository<CourseDepartment, Int> {

    @Query("select c.name from course_department c", nativeQuery = true)
    fun getDepartmentNames(): List<String>

}