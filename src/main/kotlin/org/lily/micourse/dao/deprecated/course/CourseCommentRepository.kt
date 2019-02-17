package org.lily.micourse.dao.deprecated.course

import org.lily.micourse.po.course.CourseComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */

@Repository
interface CourseCommentRepository: JpaRepository<CourseComment, Int>, JpaSpecificationExecutor<CourseComment>