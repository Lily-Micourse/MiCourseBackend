package org.lily.micourse.dao.course

import org.lily.micourse.po.course.CourseSubCommentVote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseSubCommentVoteRepository: JpaRepository<CourseSubCommentVote, Int> {

    @Query(value = "delete from course_sub_comment_vote where sub_comment_id = ? and userId = ?", nativeQuery = true)
    fun deleteByUserIdAndCommentId(subCommentId: Int, userId: Int)

    fun findByUserIdAndSubCommentId(userId: Int, subCommentId: Int) : CourseSubCommentVote?

}