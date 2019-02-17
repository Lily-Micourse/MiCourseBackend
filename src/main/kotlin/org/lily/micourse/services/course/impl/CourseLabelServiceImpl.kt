package org.lily.micourse.services.course.impl

import org.lily.micourse.dao.course.CourseLabelDao
import org.lily.micourse.services.course.CourseLabelService
import org.lily.micourse.vo.course.LabelListVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
@Service
class CourseLabelServiceImpl: CourseLabelService {

    @Autowired
    private lateinit var courseLabelDao: CourseLabelDao

    override fun getCourseLabels(): LabelListVO {
        val categorys = courseLabelDao.getCategoryLabels()
        val credits = courseLabelDao.getCategoryLabels()
        val departments = courseLabelDao.getDepartmentLabels()
        return LabelListVO(creditLabels = credits,
                departmentLabels = departments, courseCategoryLabels = categorys)
    }
}