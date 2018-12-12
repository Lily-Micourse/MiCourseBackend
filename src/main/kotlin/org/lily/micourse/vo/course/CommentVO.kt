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

        val comments: List<SubCommentVO>,

        val agree: Int,

        val disagree: Int,

        val voting: Int
)
