package org.lily.micourse.services.course

import org.lily.micourse.vo.course.CourseCommentVO
import org.lily.micourse.vo.course.CourseCommetVotingStateVO

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseCommentService {

    fun getCourseComments(courseId: Int) : List<CourseCommentVO>

    fun doComment(courseId: Int, userId: Int, content: String, term: String)

    fun doSubComment(userId: Int, content: String, commentId: Int, subCommentId: Int?)

    fun getCourseCommentVotingState(courseId: Int, userId: Int): CourseCommetVotingStateVO

}