package org.lily.micourse.dao

import org.junit.Test
import org.junit.runner.RunWith
import org.lily.micourse.dao.course.CourseCategoryRepository
import org.lily.micourse.dao.course.CourseDAO
import org.lily.micourse.dao.course.CourseDepartmentRepository
import org.lily.micourse.dao.course.CourseRepository
import org.lily.micourse.po.course.Course
import org.lily.micourse.po.course.CourseCategory
import org.lily.micourse.po.course.CourseDepartment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created on 12/12/2018.
 * Description:
 * @author iznauy
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class CourseTest {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var courseDAO: CourseDAO

    @Autowired
    lateinit var courseCategoryRepository: CourseCategoryRepository

    @Autowired
    lateinit var courseDepartmentRepository: CourseDepartmentRepository

    lateinit var courseCategory: CourseCategory

    lateinit var courseDepartment: CourseDepartment


    @Test
    fun addCourse() {
        courseCategory = courseCategoryRepository.getOne(3)
        courseDepartment = courseDepartmentRepository.getOne(1)
        val course1 = Course(courseCode = "25000101", courseName = "软件构造",
                courseCategory = courseCategory, courseDepartment = courseDepartment, credit = 3, description = "潘敏学上的一门课，" +
                "考试放在了1月，很坑")
        courseRepository.save(course1)
        val course2 = Course(courseCode = "25000103", courseName = "软件测试",
                courseCategory = courseCategory, courseDepartment = courseDepartment, credit = 3, description = "陈振宇，" +
                "这个老师就不用我多说什么了吧")
        courseRepository.save(course2)
    }

    @Test
    fun testSelect() {
        val courses = courseDAO.getCourseByCategory(PageRequest.of(0, 2), category = "必修")
        println(courses)
        println("OK")
    }



}