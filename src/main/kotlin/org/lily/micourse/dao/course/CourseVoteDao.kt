package org.lily.micourse.dao.course

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

    fun deleteSubCommentVotingRecord(subCommentVote: CourseSubCommentVote) =
            courseSubCommentVoteRepository.delete(subCommentVote)

    fun getVotingRecordByUserIdAndCommentId(userId: Int, commentId: Int) =
            courseCommentVoteRepository

}