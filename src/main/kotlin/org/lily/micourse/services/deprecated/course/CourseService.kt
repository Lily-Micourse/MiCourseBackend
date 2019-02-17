package org.lily.micourse.services.deprecated.course

import org.lily.micourse.dao.deprecated.course.CourseCategoryRepository
import org.lily.micourse.dao.deprecated.course.CourseDAO
import org.lily.micourse.dao.deprecated.course.CourseDepartmentRepository
import org.lily.micourse.dao.deprecated.course.CourseFeedbackRepository
import org.lily.micourse.po.course.Course
import org.lily.micourse.po.course.CourseFeedback
import org.lily.micourse.vo.course.CourseVO
import org.lily.micourse.vo.course.LabelListVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */
@Service
class CourseService(

        @Autowired
        var courseDAO: CourseDAO,

        @Autowired
        var courseDepartmentRepository: CourseDepartmentRepository,

        @Autowired
        var courseFeedbackRepository: CourseFeedbackRepository,

        @Autowired
        var courseCategoryRepository: CourseCategoryRepository

) {

    fun getLabelList(): LabelListVO {
        val creditLabels = courseDAO.getCreditLabels()
        val departmentLabels = courseDepartmentRepository.getDepartmentNames()
        val courseCategoryLabels = courseCategoryRepository.getCategoryNames()
        return LabelListVO(creditLabels, departmentLabels, courseCategoryLabels)
    }

    fun getCourseList(pattern: String, rankingType: CourseRankingType, page: Int, pageSize: Int) : Pair<List<Course>, Long> {
        val sort: Sort =
                when(rankingType) {
                    CourseRankingType.HOT -> Sort.by(Sort.Direction.DESC, "commentNum")
                    CourseRankingType.RECOMMENDED -> Sort.by(Sort.Direction.DESC, "commentNum")
                    CourseRankingType.LAST -> Sort.by(Sort.Direction.DESC, "id")
                }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        return courseDAO.getCourseList(page = pageRequest, pattern = pattern)
    }

    fun getCourseListByLabel(label: String, labelType: LabelType, page: Int, pageSize: Int): Pair<List<Course>, Long> {
        val pageRequest: PageRequest = PageRequest.of(page, pageSize)
        return when(labelType) {
            LabelType.DEPARTMENT -> courseDAO.getCourseByDepartment(pageRequest, label)
            LabelType.CATEGORY -> courseDAO.getCourseByCategory(pageRequest, label)
            LabelType.CREDIT -> courseDAO.getCourseByCredit(pageRequest, label.toInt())
        }
    }

    /**
     * 残疾的VO，里面的rate设置为0
     */
    private fun getCourseVO(id: Int): CourseVO {
        var courseOpt = courseDAO.getCourseById(id)
        if (courseOpt.isPresent) {
            var course = courseOpt.get()
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun calcCourseRate(courseFeedbacks: List<CourseFeedback>) =
            courseFeedbacks.stream().map { it.rate }.collect(Collectors.averagingDouble {it + 0.0})



}


enum class CourseRankingType {
    HOT, LAST, RECOMMENDED
}

enum class LabelType {
    CREDIT, DEPARTMENT, CATEGORY
}