package org.lily.micourse.vo.course

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */
data class CommentVO(

        val id: Int,

        val userId: Int,

        val nickname: String,

        val avatar: String,

        val isNJUer: Boolean,

        val content: String,

        val time: String,

        val term: String,

        val comments: List<SubComment>,

        val agree: Int,

        val disagree: Int
)

data class SubComment(

        val id: Int,

        val replyToCommentId: Int,

        val userId: Int,

        val nickname: String,

        val avatar: String,

        val isNJUer: Boolean,

        val content: String

        // to be done
)