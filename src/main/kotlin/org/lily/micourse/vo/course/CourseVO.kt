package org.lily.micourse.vo.course

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

        val rate: Float,

        val commentNum: Int

)