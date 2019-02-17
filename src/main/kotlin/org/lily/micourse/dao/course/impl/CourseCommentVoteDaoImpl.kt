package org.lily.micourse.dao.course.impl

import org.lily.micourse.dao.course.CourseCommentVoteDao
import org.lily.micourse.po.course.CourseCommentVote
import org.lily.micourse.po.course.CourseSubCommentVote
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

@Repository
class CourseCommentVoteDaoImpl: CourseCommentVoteDao {

    @Autowired
    private lateinit var courseCommentVoteRepository: CourseCommentVoteRepository

    @Autowired
    private lateinit var courseSubCommentVoteRepository: CourseSubCommentVoteRepository

    override fun findVotingByUserIdAndCourseId(userId: Int, courseId: Int): List<CourseCommentVote>
        = courseCommentVoteRepository.findAllByUserIdAndCourseId(userId, courseId)

    override fun voteComment(userId: Int, commentId: Int, agree: Boolean) {
        courseCommentVoteRepository.save(CourseCommentVote(userId = userId, commentId = commentId, agree = agree))
    }

    override fun voteSubComment(userId: Int, subCommentId: Int, agree: Boolean) {
        courseSubCommentVoteRepository.save(CourseSubCommentVote(userId = userId, subCommentId = subCommentId,
                agree = agree))
    }

    override fun deleteCommentVotingRecord(commentVote: CourseCommentVote) =
            courseCommentVoteRepository.delete(commentVote)

    override fun deleteCommentVotingRecord(userId: Int, commentId: Int) =
            courseCommentVoteRepository.deleteByUserIdAndCommentId(commentId, userId)

    override fun deleteSubCommentVotingRecord(subCommentVote: CourseSubCommentVote) =
            courseSubCommentVoteRepository.delete(subCommentVote)

    override fun deleteSubCommentVotingRecord(userId: Int, subCommentId: Int) =
            courseSubCommentVoteRepository.deleteByUserIdAndCommentId(subCommentId, userId)

    override fun getVotingRecordByUserIdAndCommentId(userId: Int, commentId: Int) =
            courseCommentVoteRepository.findByUserIdAndCommentId(userId, commentId)

    override fun getSubVotingRecordByUserIdAndSubCommentId(userId: Int, subCommentId: Int): CourseSubCommentVote?
            = courseSubCommentVoteRepository.findByUserIdAndSubCommentId(userId, subCommentId)
}