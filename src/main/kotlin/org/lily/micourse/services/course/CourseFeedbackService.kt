package org.lily.micourse.services.course

import org.lily.micourse.entity.course.CheckInFrequency
import org.lily.micourse.entity.course.CoursePressure
import org.lily.micourse.entity.course.ExamMethod
import org.lily.micourse.entity.course.Grade
import org.lily.micourse.vo.course.HasFeedBackVO

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseFeedbackService {

    fun hasFeedBack(courseId: Int, userId: Int): HasFeedBackVO

    fun addFeedBack(courseId: Int, userId: Int, rate: Double, pressure: CoursePressure,
                    grade: Grade, examine: Set<ExamMethod>, checkInFrequency: CheckInFrequency,
                    term: String)

    fun cancelCommentVoting(userId: Int, commentId: Int)

    fun cancelSubCommentVoting(userId: Int, commentId: Int, subCommentId: Int)

    fun votingComment(userId: Int, commentId: Int, agree: Boolean)

    fun votingSubComment(userId: Int, commentId: Int, subCommentId: Int, agree: Boolean)


}