package org.lily.micourse.services.course.impl

import org.lily.micourse.dao.course.CourseCommentVoteDao
import org.lily.micourse.dao.course.CourseFeedbackDao
import org.lily.micourse.entity.course.CheckInFrequency
import org.lily.micourse.entity.course.CoursePressure
import org.lily.micourse.entity.course.ExamMethod
import org.lily.micourse.entity.course.Grade
import org.lily.micourse.exception.DuplicateException
import org.lily.micourse.po.course.CourseFeedback
import org.lily.micourse.services.course.CourseFeedbackService
import org.lily.micourse.vo.course.HasFeedBackVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

@Service
class CourseFeedbackServiceImpl: CourseFeedbackService {

    @Autowired
    private lateinit var courseFeedbackDao: CourseFeedbackDao

    @Autowired
    private lateinit var courseCommentVoteDao: CourseCommentVoteDao

    override fun hasFeedBack(courseId: Int, userId: Int): HasFeedBackVO {
        val hasFeedback = courseFeedbackDao.findFeedbackByCourseIdAndUserId(courseId, userId).isPresent
        return HasFeedBackVO(hasFeedback)
    }

    override fun addFeedBack(courseId: Int, userId: Int, rate: Double, pressure: CoursePressure,
                             grade: Grade, examine: Set<ExamMethod>, checkInFrequency: CheckInFrequency, term: String) {
        val courseFeedback = CourseFeedback(courseId = courseId, userId =  userId, rate = rate,
                pressure = pressure, grade = grade, evalPaper = examine.contains(ExamMethod.PAPER),
                evalAttendance = examine.contains(ExamMethod.ATTENDANCE),
                evalClosedBookExam = examine.contains(ExamMethod.CLOSE_BOOK_EXAM),
                evalOpenBookExam = examine.contains(ExamMethod.OPEN_BOOK_EXAM),
                evalOthers = examine.contains(ExamMethod.OTHERS),
                evalTeamWork = examine.contains(ExamMethod.TEAMWORK),
                checkInFrequency = checkInFrequency, term = term)
        courseFeedbackDao.addFeedback(courseFeedback)
    }

    override fun cancelCommentVoting(userId: Int, commentId: Int) {
        courseCommentVoteDao.deleteCommentVotingRecord(userId, commentId)
    }

    override fun cancelSubCommentVoting(userId: Int, commentId: Int, subCommentId: Int) {
        courseCommentVoteDao.deleteSubCommentVotingRecord(userId, subCommentId)
    }

    override fun votingComment(userId: Int, commentId: Int, agree: Boolean) {
        val commentVote = courseCommentVoteDao.getVotingRecordByUserIdAndCommentId(userId, commentId)
        if (commentVote == null)
            courseCommentVoteDao.voteComment(userId, commentId, agree)
        else {
            if (commentVote.agree == agree)
                throw DuplicateException("已经表态过")
            courseCommentVoteDao.deleteCommentVotingRecord(commentVote)
            courseCommentVoteDao.voteComment(userId, commentId, agree)
        }
    }

    override fun votingSubComment(userId: Int, commentId: Int, subCommentId: Int, agree: Boolean) {
        val subCommentVote = courseCommentVoteDao.
                getSubVotingRecordByUserIdAndSubCommentId(userId, subCommentId)
        if (subCommentVote == null)
            courseCommentVoteDao.voteComment(userId, subCommentId, agree)
        else {
            if (subCommentVote.agree == agree)
                throw DuplicateException("已经表态过")
            courseCommentVoteDao.deleteSubCommentVotingRecord(subCommentVote)
            courseCommentVoteDao.voteSubComment(userId, subCommentId, agree)
        }
    }
}