package org.lily.micourse.dao.course.impl

import org.lily.micourse.po.course.CourseSubCommentVote
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
interface CourseSubCommentVoteRepository: CrudRepository<CourseSubCommentVote, Int> {

    @Query(value = "delete from course_sub_comment_vote where sub_comment_id = ? and userId = ?", nativeQuery = true)
    fun deleteByUserIdAndCommentId(subCommentId: Int, userId: Int)

    fun findByUserIdAndSubCommentId(userId: Int, subCommentId: Int) : CourseSubCommentVote

    fun findAllByUserIdAndCourseId(userId: Int, courseId: Int) : List<CourseSubCommentVote>

    fun findAllByCourseId(courseId: Int): List<CourseSubCommentVote>

}