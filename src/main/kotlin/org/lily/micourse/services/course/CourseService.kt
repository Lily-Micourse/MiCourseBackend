package org.lily.micourse.services.course

import org.lily.micourse.dao.course.CourseCategoryRepository
import org.lily.micourse.dao.course.CourseDao
import org.lily.micourse.dao.course.CourseDepartmentRepository
import org.lily.micourse.dao.course.CourseFeedbackRepository
import org.lily.micourse.entity.course.Course
import org.lily.micourse.entity.course.CourseFeedback
import org.lily.micourse.vo.course.CourseDetailVO
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
        var courseDao: CourseDao,

        @Autowired
        var courseDepartmentRepository: CourseDepartmentRepository,

        @Autowired
        var courseCategoryRepository: CourseCategoryRepository,

        @Autowired
        var courseFeedbackRepository: CourseFeedbackRepository
) {

    fun getLabelList(): LabelListVO {
        val creditLabels = courseDao.getCreditLabels()
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
        return courseDao.getCourseList(page = pageRequest, pattern = pattern)
    }

    fun getCourseListByLabel(label: String, labelType: LabelType, page: Int, pageSize: Int): Pair<List<Course>, Long> {
        val pageRequest: PageRequest = PageRequest.of(page, pageSize)
        return when(labelType) {
            LabelType.DEPARTMENT -> courseDao.getCourseByDepartment(pageRequest, label)
            LabelType.CATEGORY -> courseDao.getCourseByCategory(pageRequest, label)
            LabelType.CREDIT -> courseDao.getCourseByCredit(pageRequest, label.toInt())
        }
    }

    /**
     * 残疾的VO，里面的rate设置为0
     */
    private fun getCourseVO(id: Int): CourseVO {
        var courseOpt = courseDao.getCourseById(id)
        if (courseOpt.isPresent) {
            var course = courseOpt.get()
        }
        return null
    }

    private fun calcCourseRate(courseFeedbacks: List<CourseFeedback>) =
            courseFeedbacks.stream().map { it.rate }.collect(Collectors.averagingDouble {it + 0.0})

    private fun genPressureIndexes(courseFeedbacks: List<CourseFeedback>) = courseFeedbacks
                .groupBy { it.pressure.name }.mapValues { it.value.size }


    private fun genExamineIndexes(courseFeedbacks: List<CourseFeedback>): Map<String, Int> {

        val paper = courseFeedbacks.count { it.evalPaper }
        val attendence = courseFeedbacks.count { it.evalAttendance }
        val teamWork = courseFeedbacks.count { it.evalTeamWork }
        val closedBookExam = courseFeedbacks.count { it.evalClosedBookExam }
        val openBookExam = courseFeedbacks.count { it.evalOpenBookExam }
        val others = courseFeedbacks.count { it.evalOthers }
        return mapOf(
                Pair("essay", paper), Pair("checkIn", attendence), Pair("discussion", teamWork),
                Pair("closedBookExam", closedBookExam), Pair("openBookExam", openBookExam),
                Pair("others", others)
        )

    }

    private fun genGradeIndexes(courseFeedbacks: List<CourseFeedback>): Map<String, Int> {
        var A = 0
        var B = 0
        var C = 0
        var D = 0
        var E = 0
        courseFeedbacks
                .map { it.score }
                .forEach {
                    when {
                        it >= 90 -> A++
                        it >= 80 -> B++
                        it >= 70 -> C++
                        it >= 60 -> D++
                        else -> E++
                    }
                }
        return mapOf(
                Pair("A", A), Pair("B", B), Pair("C", C), Pair("D", D), Pair("E", E)
        )
    }

    private fun genCheckInIndexes(courseFeedbacks: List<CourseFeedback>) = courseFeedbacks.
                groupBy { it.checkInFrequency.toString() }.mapValues { it.value.size }

    fun getCourseDetailVO(id: Int): CourseDetailVO {
        val courseVO = getCourseVO(id)
        val courseFeedbacks = courseFeedbackRepository.findByCourseId(id)
        val pressureIndexes = genPressureIndexes(courseFeedbacks)
        val examineIndexes = genExamineIndexes(courseFeedbacks)
        val gradeIndexes = genGradeIndexes(courseFeedbacks)
        val checkInIndexes = genCheckInIndexes(courseFeedbacks)
        return CourseDetailVO(courseVO, pressureIndexes, examineIndexes, gradeIndexes, checkInIndexes)
    }

}


enum class CourseRankingType {
    HOT, LAST, RECOMMENDED
}

enum class LabelType {
    CREDIT, DEPARTMENT, CATEGORY
}