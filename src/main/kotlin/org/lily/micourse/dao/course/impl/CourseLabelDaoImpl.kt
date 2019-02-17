package org.lily.micourse.dao.course.impl

import org.lily.micourse.dao.course.CourseLabelDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
@Repository
class CourseLabelDaoImpl: CourseLabelDao {

    @Autowired
    private lateinit var categoryRepository: CourseCategoryRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var courseDepartmentRepository: CourseDepartmentRepository


    override fun getCreditLabels(): List<String> = courseRepository.getCreditLabels().map { it.toString() }

    override fun getDepartmentLabels(): List<String> = courseDepartmentRepository.getDepartmentNames()

    override fun getCategoryLabels(): List<String> = categoryRepository.getCategoryNames()
}