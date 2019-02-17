package org.lily.micourse.dao

import org.junit.Test
import org.junit.runner.RunWith
import org.lily.micourse.dao.deprecated.course.CourseDepartmentRepository
import org.lily.micourse.po.course.CourseDepartment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created on 12/12/2018.
 * Description:
 * @author iznauy
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class CourseDepartmentTest {

    @Autowired
    lateinit var courseDepartmentRepository: CourseDepartmentRepository

    @Test
    fun test0() {
        val courseDepartment1 = CourseDepartment(name = "软件学院")
        val courseDepartment2 = CourseDepartment(name = "计算机系")
        courseDepartmentRepository.save(courseDepartment1)
        courseDepartmentRepository.saveAndFlush(courseDepartment2)
    }


}