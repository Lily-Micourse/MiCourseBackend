package org.lily.micourse.vo.course

import org.lily.micourse.po.course.CourseSubComment
import org.lily.micourse.po.user.User

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
data class CourseSubCommentVO(

        val id: Int,

        val replyToCommentId: Int, // 根评论id

        val userId: Int,

        val nickname: String,

        val avatar: String,

        val isNJUer: Boolean,

        val content: String,

        val time: String,

        val courseSubComments: MutableList<CourseSubCommentVO>,

        val agree: Int,

        val disagree: Int

) {
    constructor(courseSubComment: CourseSubComment, user: User, agree: Int, disagree: Int)
        : this(courseSubComment.id, courseSubComment.courseComment.id, user.id, user.username,
            user.portraitUrl, user.isSchoolEmailValidated, courseSubComment.content, courseSubComment.addTime.toString(),
            mutableListOf<CourseSubCommentVO>(), agree, disagree)
}