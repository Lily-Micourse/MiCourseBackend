package org.lily.micourse.vo.course

import org.lily.micourse.po.course.CourseComment

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

        val commentCourses: MutableList<CourseSubCommentVO>,

        var agree: Int,

        var disagree: Int

) {
    constructor(courseComment: CourseComment)
     : this (courseComment.id, courseComment.user.id, courseComment.user.username, courseComment.user.portraitUrl,
            courseComment.user.isSchoolEmailValidated, courseComment.content, courseComment.addTime.toString(),
            courseComment.semester, mutableListOf<CourseSubCommentVO>(), 0, 0)
}