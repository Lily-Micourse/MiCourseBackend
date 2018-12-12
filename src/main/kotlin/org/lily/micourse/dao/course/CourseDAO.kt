package org.lily.micourse.dao.course

import org.lily.micourse.po.course.Course
import org.lily.micourse.po.course.CourseCategory
import org.lily.micourse.po.course.CourseDepartment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors
import javax.persistence.criteria.Predicate

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */

@Repository
class CourseDAO(

        @Autowired
        var courseRepository: CourseRepository) {

    fun getCreditLabels(): List<String> =
            courseRepository.getCreditLabels().stream().map { it.toString() }
                    .collect(Collectors.toList())

    fun getCourseById(id: Int): Optional<Course> =
            courseRepository.findById(id)

    fun getCourseList(page: PageRequest, pattern: String): Pair<List<Course>, Long> {
        val pageResult = courseRepository.findAll({ p0, p1, p2 ->
            val predicate1: Predicate = p2.like(p0.get("courseName"), "%$pattern%")
            val predicate2: Predicate = p2.like(p0.get("description"), "%$pattern%")
            p1.where(p2.or(predicate1, predicate2))
            null
        }, page)
        return Pair(pageResult.content, pageResult.totalElements)
    }

    fun getCourseByCategory(page: PageRequest, category: String): Pair<List<Course>, Long> {
        val pageResult = courseRepository.findAll({ p0, p1, p2 ->
            val predicate: Predicate = p2.like(p0.get<CourseCategory>("courseCategory").get<String>("name"), "%$category%")
            p1.where(predicate)
            null
        }, page)
        return Pair(pageResult.content, pageResult.totalElements)
    }

    fun getCourseByDepartment(page: PageRequest, department: String) : Pair<List<Course>, Long> {

        val pageResult = courseRepository.findAll({ p0, p1, p2 ->
            val predicate: Predicate = p2.like(p0.get<CourseDepartment>("courseDepartment").get<String>("name"), "%$department%")
            p1.where(predicate)
            null
        }, page)
        return Pair(pageResult.content, pageResult.totalElements)
    }

    fun getCourseByCredit(page: PageRequest, credit: Int) : Pair<List<Course>, Long> {
        val pageResult = courseRepository.findAll({ p0, p1, p2 ->
            val predicate: Predicate = p2.equal(p0.get<Int>("credit"), credit)
            p1.where(predicate)
            null
        }, page)
        return Pair(pageResult.content, pageResult.totalElements)
    }

}