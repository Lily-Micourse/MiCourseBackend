package org.lily.micourse.vo.course

import org.lily.micourse.po.course.CourseComment
import org.lily.micourse.po.user.User

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
    constructor(courseComment: CourseComment, user: User, agree: Int, disagree: Int)
    : this(courseComment.id, courseComment.userId, user.username, user.portraitUrl, user.isSchoolEmailValidated,
            courseComment.content, courseComment.addTime.toString(), courseComment.semester, mutableListOf<CourseSubCommentVO>(),
            agree, disagree)
}