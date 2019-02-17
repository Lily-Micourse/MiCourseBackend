package org.lily.micourse.dao.deprecated.course

import org.lily.micourse.po.course.Course
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Created on 16/12/2018.
 * Description:
 * @author iznauy
 */
@Repository
class CourseTermDAO {

    @Autowired
    lateinit var courseTermRepository: CourseTermRepository

    fun getCourseTermsByCourseId(id: Int) : List<String> {
        return courseTermRepository.findAll{p0, p1, p2 ->
            p1.where(p2.equal(p0.get<Course>("course").get<Int>("id"), id))
            null
        }.map { it.term }
    }

}