package org.lily.micourse.services.course

import org.lily.micourse.dao.course.CourseCategoryRepository
import org.lily.micourse.dao.course.CourseDao
import org.lily.micourse.dao.course.CourseDepartmentRepository
import org.lily.micourse.dao.course.CourseFeedbackRepository
import org.lily.micourse.entity.course.CourseFeedback
import org.lily.micourse.vo.course.CourseDetailVO
import org.lily.micourse.vo.course.CourseVO
import org.lily.micourse.vo.course.LabelListVO
import org.springframework.beans.factory.annotation.Autowired
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

    /**
     * 残疾的VO，里面的rate设置为0, commentNum设置为0
     */
    private fun getCourseVO(id: Int): CourseVO {
        var courseOpt = courseDao.getCourseById(id)
        if (courseOpt.isPresent) {
            var course = courseOpt.get()
            return CourseVO(id, )
        }
        return null
    }

    private fun calcCourseRate(courseFeedbacks: List<CourseFeedback>) =
            courseFeedbacks.stream().map { it.rate }.collect(Collectors.averagingDouble {it + 0.0})

    private fun genPressureIndexes(courseFeedbacks: List<CourseFeedback>): Map<String, Int> {
        val pressureIndexes: MutableMap<String, Int> = mutableMapOf()
        for (courseFeedback: CourseFeedback in courseFeedbacks) {
            val key = courseFeedback.pressure.toString()
            val current = pressureIndexes.getOrDefault(key, 0)
            if (current == 0)
                pressureIndexes[key] = 1
            else
                pressureIndexes[key] = current + 1
        }
        return pressureIndexes
    }

    private fun genExamineIndexes(courseFeedbacks: List<CourseFeedback>): Map<String, Int> {

        var paper = 0
        var attendence = 0
        var teamWork = 0
        var closedBookExam = 0
        var openBookExam = 0
        var others = 0
        for (courseFeedback: CourseFeedback in courseFeedbacks) {
            if (courseFeedback.evalPaper)
                paper++
            if (courseFeedback.evalAttendance)
                attendence++
            if (courseFeedback.evalTeamWork)
                teamWork++
            if (courseFeedback.evalClosedBookExam)
                closedBookExam++
            if (courseFeedback.evalOpenBookExam)
                openBookExam++
            if (courseFeedback.evalOthers)
                others++

        }
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

    private fun genCheckInIndexes(courseFeedbacks: List<CourseFeedback>): Map<String, Int> {
        val checkInIndexes: MutableMap<String, Int> = mutableMapOf()
        for (courseFeedback: CourseFeedback in courseFeedbacks) {
            val key = courseFeedback.checkInFrequency.toString()
            val current = checkInIndexes.getOrDefault(key, 0)
            if (current == 0)
                checkInIndexes[key] = 1
            else
                checkInIndexes[key] = current + 1
        }
        return checkInIndexes
    }

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