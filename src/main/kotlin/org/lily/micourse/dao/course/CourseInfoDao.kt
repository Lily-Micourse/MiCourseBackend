package org.lily.micourse.dao.course

import org.lily.micourse.po.course.Course
import org.springframework.data.domain.PageRequest
import java.util.*

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
interface CourseInfoDao {

    fun getCourseById(id: Int) : Optional<Course>

    fun getCourseList(page: PageRequest, pattern: String = ""): Pair<List<Course>, Long>

    fun getCourseAndTotalCountByCategory(page: PageRequest, category: String): Pair<List<Course>, Long>

    fun getCourseAndTotalCountByDepartment(page: PageRequest, department: String) : Pair<List<Course>, Long>

    fun getCourseAndTotalCountByCredit(page: PageRequest, credit: Int) : Pair<List<Course>, Long>
}