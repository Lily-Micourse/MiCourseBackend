package org.lily.micourse.dao.course

import org.lily.micourse.po.course.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
interface CourseTermRepository: JpaRepository<CourseTerm, Int>, JpaSpecificationExecutor<CourseTerm>