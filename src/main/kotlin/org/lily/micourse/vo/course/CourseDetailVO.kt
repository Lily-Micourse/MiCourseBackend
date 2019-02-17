package org.lily.micourse.vo.course

import org.lily.micourse.po.course.Course

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */

data class CourseDetailVO (

        val id: Int,

        val name: String,

        val rate: Double,

        val cover: String,

        val credit: Int,

        val type: String, // category

        val pressureIndexes: Map<String, Int>,

        val examineIndexes: Map<String, Int>,

        val gradeIndexes: Map<String, Int>,

        val checkInIndexes: Map<String, Int>,

        val description: String

) {

    constructor(course: Course, rate: Double, pressureIndexes: Map<String, Int>, examineIndexes: Map<String, Int>,
                gradeIndexes: Map<String, Int>, checkInIndexes: Map<String, Int>)
        : this(course.id, course.courseName, rate, course.courseIcon, course.credit, course.courseCategory,
            pressureIndexes, examineIndexes, gradeIndexes, checkInIndexes, course.description)

}