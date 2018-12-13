package org.lily.micourse.vo.course

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
data class CourseCommentVO (

        val id: Int,

        val userId: Int,

        val nickname: String,

        val avatar: String,

        val isNJUer: Boolean,

        val content: String,

        val time: String,

        val term: String,

        val commentCourses: List<CourseSubCommentVO>,

        val agree: Int,

        val disagree: Int,

        val voting: Int

)