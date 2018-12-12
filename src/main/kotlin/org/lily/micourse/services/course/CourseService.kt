package org.lily.micourse.services.course

import org.lily.micourse.dao.course.CourseCategoryRepository
import org.lily.micourse.dao.course.CourseDAO
import org.lily.micourse.dao.course.CourseDepartmentRepository
import org.lily.micourse.dao.course.CourseFeedbackRepository
import org.lily.micourse.entity.course.Score
import org.lily.micourse.po.course.Course
import org.lily.micourse.po.course.CourseFeedback
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
        var A = courseFeedbacks.count { it.score == Score.A }
        var B = courseFeedbacks.count { it.score == Score.B }
        var C = courseFeedbacks.count { it.score == Score.C }
        var D = courseFeedbacks.count { it.score == Score.D }
        var E = courseFeedbacks.count { it.score == Score.E }
        return mapOf(
                Pair("A", A), Pair("B", B), Pair("C", C), Pair("D", D), Pair("E", E)
        )
    }

    private fun genCheckInIndexes(courseFeedbacks: List<CourseFeedback>) = courseFeedbacks.
                groupBy { it.checkInFrequency.toString() }.mapValues { it.value.size }

    fun getCourseDetailVO(id: Int): CourseDetailVO {
        val courseVO = getCourseVO(id)
        val courseFeedbacks = courseFeedbackRepository.findAllByCourseId(id)
        val pressureIndexes = genPressureIndexes(courseFeedbacks)
        val examineIndexes = genExamineIndexes(courseFeedbacks)
        val gradeIndexes = genGradeIndexes(courseFeedbacks)
        val checkInIndexes = genCheckInIndexes(courseFeedbacks)
  //      return CourseDetailVO(courseVO, pressureIndexes, examineIndexes, gradeIndexes, checkInIndexes)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


enum class CourseRankingType {
    HOT, LAST, RECOMMENDED
}

enum class LabelType {
    CREDIT, DEPARTMENT, CATEGORY
}