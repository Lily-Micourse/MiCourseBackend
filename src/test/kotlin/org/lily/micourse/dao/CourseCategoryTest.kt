package org.lily.micourse.dao

import org.junit.Test
import org.junit.runner.RunWith
import org.lily.micourse.dao.course.CourseCategoryRepository
import org.lily.micourse.po.course.CourseCategory
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
class CourseCategoryTest {

    @Autowired
    lateinit var courseCategoryRepository: CourseCategoryRepository

    @Test
    fun test0() {
        val courseCategory = CourseCategory(name = "通修")
        courseCategoryRepository.saveAndFlush(courseCategory)

    }

}
