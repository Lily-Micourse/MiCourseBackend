package org.lily.micourse.vo.course

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

        val courseSubComments: List<CourseSubCommentVO>,

        val agree: Int,

        val disagree: Int,

        val voting: Int
)