package org.lily.micourse.vo.course

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */

data class CourseDetailVO (

        val id: Int,

        val name: String,

        val cover: String,

        val credit: Int,

        val rate: Float,

        val commentNum: Int,

        val pressureIndexes: Map<String, Int>,

        val examineIndexes: Map<String, Int>,

        val gradeIndexes: Map<String, Int>,

        val checkInIndexes: Map<String, Int>
) {

    constructor(courseVO: CourseVO, pressureIndexes: Map<String, Int>,
                examineIndexes: Map<String, Int>, gradeIndexes: Map<String, Int>,
                checkInIndexes: Map<String, Int>) :
            this(courseVO.id, courseVO.name, courseVO.cover,
            courseVO.credit, courseVO.rate, courseVO.commentNum,
                    pressureIndexes, examineIndexes, gradeIndexes, checkInIndexes)

}