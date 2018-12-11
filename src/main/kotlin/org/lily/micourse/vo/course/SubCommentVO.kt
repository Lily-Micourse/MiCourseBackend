package org.lily.micourse.vo.course

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
data class SubCommentVO(

        val id: Int,

        val replyToCommentId: Int, // 根评论id

        val userId: Int,

        val nickname: String,

        val avatar: String,

        val isNJUer: Boolean,

        val content: String,

        val subComments: List<SubCommentVO>,

        val agree: Int,

        val disagree: Int,

        val voting: Int
)