package org.lily.micourse.dao.course.impl

import org.lily.micourse.po.course.CourseCommentVote
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseCommentVoteRepository : CrudRepository<CourseCommentVote, Int> {

    fun findByUserIdAndCommentId(userId: Int, commentId: Int) : CourseCommentVote?

    @Query(value = "delete from course_comment_vote where comment_id = ? and userId = ?", nativeQuery = true)
    fun deleteByUserIdAndCommentId(commentId: Int, userId: Int)

}