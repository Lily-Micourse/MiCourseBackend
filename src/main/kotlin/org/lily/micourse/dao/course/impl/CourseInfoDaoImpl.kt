package org.lily.micourse.dao.course.impl

import org.lily.micourse.dao.course.CourseInfoDao
import org.lily.micourse.po.course.Course
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.criteria.Predicate

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
@Repository
class CourseInfoDaoImpl: CourseInfoDao {

    @Autowired
    private lateinit var courseRepository: CourseRepository

    override fun getCourseById(id: Int): Optional<Course> = courseRepository.findById(id)

    override fun getCourseList(page: PageRequest, pattern: String): Pair<List<Course>, Long> {
        val pageResult = courseRepository.findAll({ p0, p1, p2 ->
            val predicate1: Predicate = p2.like(p0.get("courseName"), "%$pattern%")
            val predicate2: Predicate = p2.like(p0.get("description"), "%$pattern%")
            val predicate3: Predicate = p2.like(p0.get("courseDepartment"), "%$pattern%")
            val predicate4: Predicate = p2.like(p0.get("courseCategory"), "%$pattern%")
            p1.where(p2.or(p2.or(predicate1, predicate2), p2.or(predicate3, predicate4)))
            null
        }, page)
        return Pair(pageResult.content, pageResult.totalElements)
    }

    override fun getCourseAndTotalCountByCategory(page: PageRequest, category: String): Pair<List<Course>, Long> {
        val pageResult = courseRepository.findAll({p0, p1, p2 ->
            val predicate: Predicate = p2.like(p0.get("courseCategory"), "%$category%")
            p1.where(predicate)
            null
        }, page)
        return Pair(pageResult.content, pageResult.totalElements)
    }

    override fun getCourseAndTotalCountByDepartment(page: PageRequest, department: String): Pair<List<Course>, Long> {
        val pageResult = courseRepository.findAll({p0, p1, p2 ->
            val predicate: Predicate = p2.like(p0.get("courseDepartment"), "%$department%")
            p1.where(predicate)
            null
        }, page)
        return Pair(pageResult.content, pageResult.totalElements)
    }

    override fun getCourseAndTotalCountByCredit(page: PageRequest, credit: Int): Pair<List<Course>, Long> {
        val pageResult = courseRepository.findAll({p0, p1, p2 ->
            val predicate: Predicate = p2.equal(p0.get<Int>("credit"), credit)
            p1.where(predicate)
            null
        }, page)
        return Pair(pageResult.content, pageResult.totalElements)
    }
}