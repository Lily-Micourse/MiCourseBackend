package org.lily.micourse.services.course.impl

import org.lily.micourse.dao.course.CourseFeedbackDao
import org.lily.micourse.dao.course.CourseInfoDao
import org.lily.micourse.entity.course.Score
import org.lily.micourse.exception.UnexpectedParamValueException
import org.lily.micourse.po.course.CourseFeedback
import org.lily.micourse.services.course.CourseDetailInfoService
import org.lily.micourse.vo.course.CourseDetailVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
@Service
class CourseDetailInfoServiceImpl: CourseDetailInfoService {

    @Autowired
    private lateinit var courseInfoDao: CourseInfoDao

    @Autowired
    private lateinit var courseFeedbackDao: CourseFeedbackDao

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
        val A = courseFeedbacks.count { it.score == Score.A }
        val B = courseFeedbacks.count { it.score == Score.B }
        val C = courseFeedbacks.count { it.score == Score.C }
        val D = courseFeedbacks.count { it.score == Score.D }
        val E = courseFeedbacks.count { it.score == Score.E }
        return mapOf(
                Pair("A", A), Pair("B", B), Pair("C", C), Pair("D", D), Pair("E", E)
        )
    }

    private fun genCheckInIndexes(courseFeedbacks: List<CourseFeedback>) = courseFeedbacks.
            groupBy { it.checkInFrequency.toString() }.mapValues { it.value.size }

    private fun genPressureIndexes(courseFeedbacks: List<CourseFeedback>) = courseFeedbacks
            .groupBy { it.pressure.name }.mapValues { it.value.size }


    override fun getCourseDetailVO(id: Int): CourseDetailVO {
        val course = courseInfoDao.getCourseById(id).orElse(null) ?: throw UnexpectedParamValueException()
        val courseFeedbacks = courseFeedbackDao.findFeedbacksByCourseId(id)
        val rate = courseFeedbacks.map { it.rate }.average()

        val pressureIndexes = genPressureIndexes(courseFeedbacks)
        val examineIndexes = genExamineIndexes(courseFeedbacks)
        val gradeIndexes = genGradeIndexes(courseFeedbacks)
        val checkInIndexes = genCheckInIndexes(courseFeedbacks)

        return CourseDetailVO(course, rate, examineIndexes,
                pressureIndexes, gradeIndexes, checkInIndexes)

    }
}