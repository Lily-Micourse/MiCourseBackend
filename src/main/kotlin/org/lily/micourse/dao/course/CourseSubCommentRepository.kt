package org.lily.micourse.dao.course

import org.lily.micourse.po.course.CourseSubComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseSubCommentRepository: JpaRepository<CourseSubComment, Int>