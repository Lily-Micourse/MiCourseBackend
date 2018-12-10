package org.lily.micourse.dao.course

import org.lily.micourse.entity.course.Course
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */

@Repository
class CourseDao (

        @Autowired
        var courseRepository: CourseRepository) {

    fun getCreditLabels(): List<String> =
            courseRepository.getCreditLabels().stream().map { it.toString() }
                    .collect(Collectors.toList())

    fun getCourseById(id: Int): Optional<Course> =
            courseRepository.findById(id)

    fun getCourseList(sort: Sort, page: PageRequest): List<Course> {
        courseRepository.findAll()
    }

}