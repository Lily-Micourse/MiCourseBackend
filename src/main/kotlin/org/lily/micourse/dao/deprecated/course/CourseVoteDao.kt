package org.lily.micourse.dao.deprecated.course

import org.lily.micourse.po.course.CourseCommentVote
import org.lily.micourse.po.course.CourseSubCommentVote
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
class CourseVoteDao (

        @Autowired
        var courseCommentVoteRepository: CourseCommentVoteRepository,

        @Autowired
        var courseSubCommentVoteRepository: CourseSubCommentVoteRepository

) {

    fun deleteCommentVotingRecord(commentVote: CourseCommentVote) =
            courseCommentVoteRepository.delete(commentVote)

    fun deleteCommentVotingRecord(userId: Int, commentId: Int) =
            courseCommentVoteRepository.deleteByUserIdAndCommentId(commentId, userId)

    fun deleteSubCommentVotingRecord(subCommentVote: CourseSubCommentVote) =
            courseSubCommentVoteRepository.delete(subCommentVote)

    fun deleteSubCommentVotingRecord(userId: Int, subCommentId: Int) =
            courseSubCommentVoteRepository.deleteByUserIdAndCommentId(subCommentId, userId)

    fun getVotingRecordByUserIdAndCommentId(userId: Int, commentId: Int) =
            courseCommentVoteRepository.findByUserIdAndCommentId(userId, commentId)

    fun getSubVotingRecordByUserIdAndSubCommentId(userId: Int, subCommentId: Int) =
            courseSubCommentVoteRepository.findByUserIdAndSubCommentId(userId, subCommentId)

}