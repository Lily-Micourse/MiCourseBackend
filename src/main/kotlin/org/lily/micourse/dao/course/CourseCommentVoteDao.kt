package org.lily.micourse.dao.course

import org.lily.micourse.po.course.CourseCommentVote
import org.lily.micourse.po.course.CourseSubCommentVote

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseCommentVoteDao {

    fun deleteCommentVotingRecord(commentVote: CourseCommentVote)

    fun deleteCommentVotingRecord(userId: Int, commentId: Int)

    fun deleteSubCommentVotingRecord(subCommentVote: CourseSubCommentVote)

    fun deleteSubCommentVotingRecord(userId: Int, subCommentId: Int)

    fun getVotingRecordByUserIdAndCommentId(userId: Int, commentId: Int): CourseCommentVote?

    fun getSubVotingRecordByUserIdAndSubCommentId(userId: Int, subCommentId: Int): CourseSubCommentVote?

}