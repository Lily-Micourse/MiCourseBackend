package org.lily.micourse.dao.course

import com.sun.org.apache.xpath.internal.operations.Bool
import org.lily.micourse.po.course.CourseCommentVote
import org.lily.micourse.po.course.CourseSubCommentVote

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseCommentVoteDao {

    fun voteComment(userId: Int, commentId: Int, agree: Boolean)

    fun voteSubComment(userId: Int, subCommentId: Int, agree: Boolean)

    fun deleteCommentVotingRecord(commentVote: CourseCommentVote)

    fun deleteCommentVotingRecord(userId: Int, commentId: Int)

    fun deleteSubCommentVotingRecord(subCommentVote: CourseSubCommentVote)

    fun deleteSubCommentVotingRecord(userId: Int, subCommentId: Int)

    fun getVotingRecordByUserIdAndCommentId(userId: Int, commentId: Int): CourseCommentVote?

    fun getSubVotingRecordByUserIdAndSubCommentId(userId: Int, subCommentId: Int): CourseSubCommentVote?

}