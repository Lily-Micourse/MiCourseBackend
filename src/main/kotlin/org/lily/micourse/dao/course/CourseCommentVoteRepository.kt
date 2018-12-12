package org.lily.micourse.dao.course

import org.lily.micourse.po.course.CourseCommentVote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseCommentVoteRepository : JpaRepository<CourseCommentVote, Int> {

    fun findByUserIdAndCommentId(userId: Int, commentId: Int) : CourseCommentVote?

    @Query(value = "delete from course_comment_vote where comment_id = ? and userId = ?", nativeQuery = true)
    fun deleteByUserIdAndCommentId(commentId: Int, userId: Int)

}