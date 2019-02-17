package org.lily.micourse.dao.course.impl

import org.lily.micourse.po.course.CourseSubComment
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseSubCommentRepository: CrudRepository<CourseSubComment, Int>, JpaSpecificationExecutor<CourseSubComment>