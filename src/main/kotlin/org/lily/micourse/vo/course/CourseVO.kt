package org.lily.micourse.vo.course

import org.lily.micourse.po.course.Course

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */

data class CourseVO (

        val id: Int,

        val name: String,

        val cover: String,

        val credit: Int,

        val rate: Double,

        val commentNum: Int,

        val department: String,

        val category: String

) {
    constructor(course: Course, courseRate: Double, commentNum: Int)
        :this(course.id, course.courseName, course.courseIcon, course.credit, courseRate, commentNum,
            course.courseDepartment, course.courseCategory)
}