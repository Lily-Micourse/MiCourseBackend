package org.lily.micourse.vo.course

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */

data class CourseDetailVO (

        val id: Int,

        val name: String,

        val rate: Float,

        val cover: String,

        val credit: Int,

        val type: String, // category

        val hasFeedback: Boolean,

        val pressureIndexes: Map<String, Int>,

        val examineIndexes: Map<String, Int>,

        val gradeIndexes: Map<String, Int>,

        val checkInIndexes: Map<String, Int>
) {


}