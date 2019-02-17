package org.lily.micourse.services.deprecated.course

import org.lily.micourse.dao.deprecated.course.*
import org.lily.micourse.entity.course.Grade
import org.lily.micourse.po.course.Course
import org.lily.micourse.po.course.CourseFeedback
import org.lily.micourse.vo.course.CourseDetailVO
import org.lily.micourse.vo.course.CourseVO
import org.lily.micourse.vo.course.LabelListVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

/**
 * Created on 16/12/2018.
 * Description:
 * @author iznauy
 */
@Service
class CourseInfoServiceImpl: CourseInfoService {

    @Autowired
    lateinit var courseDAO: CourseDAO

    @Autowired
    lateinit var courseTermDAO: CourseTermDAO

    @Autowired
    lateinit var courseCommentDAO: CourseCommentDAO

    @Autowired
    lateinit var courseDepartmentRepository: CourseDepartmentRepository

    @Autowired
    lateinit var courseFeedbackRepository: CourseFeedbackRepository

    @Autowired
    lateinit var courseCategoryRepository: CourseCategoryRepository

    override fun getTermList(courseId: Int): List<String> =
            courseTermDAO.getCourseTermsByCourseId(courseId)

    override fun getLabelList(): LabelListVO {
        val creditLabels = courseDAO.getCreditLabels()
        val departmentLabels = courseDepartmentRepository.getDepartmentNames()
        val courseCategoryLabels = courseCategoryRepository.getCategoryNames()
        return LabelListVO(creditLabels, departmentLabels, courseCategoryLabels)
    }

    private fun getCourseRateAndCommentNumByIds(ids: Collection<Int>) : Pair<Map<Int, Double>, Map<Int, Int>> {

        val courseIdToCommentCount = courseCommentDAO.getCommentCountByIds(ids).toMap()
        val courseIdToRate = courseFeedbackRepository.getCourseRatesByIds(ids).toMap()
        return Pair(courseIdToRate, courseIdToCommentCount)
    }

    private fun convertEntityToVOs(basicInfo: Pair<List<Course>, Long>) : Pair<List<CourseVO>, Long> {
        val courseList = basicInfo.first
        val totalCourse = basicInfo.second

        val tempPair = getCourseRateAndCommentNumByIds(courseList.map { it.id });
        val courseIdToCommentCount = tempPair.second.toMap()
        val courseIdToRate = tempPair.first
        val courseVOList: List<CourseVO> = courseList.map { CourseVO(course = it, courseRate = courseIdToRate[it.id]!!, commentNum =
        courseIdToCommentCount[it.id]!!) }
        return Pair(courseVOList, totalCourse)
    }

    override fun getCourseList(pattern: String, rankingType: CourseRankingType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long> {
        val sort: Sort =
                when(rankingType) {
                    CourseRankingType.HOT -> Sort.by(Sort.Direction.DESC, "commentNum")
                    CourseRankingType.RECOMMENDED -> Sort.by(Sort.Direction.DESC, "commentNum")
                    CourseRankingType.LAST -> Sort.by(Sort.Direction.DESC, "id")
                }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        val basicInfo = courseDAO.getCourseList(page = pageRequest, pattern = pattern)
        return convertEntityToVOs(basicInfo)
    }

    override fun getCourseListByLabel(label: String, labelType: LabelType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long> {
        val pageRequest: PageRequest = PageRequest.of(page, pageSize)
        val basicInfo =  when(labelType) {
            LabelType.DEPARTMENT -> courseDAO.getCourseByDepartment(pageRequest, label)
            LabelType.CATEGORY -> courseDAO.getCourseByCategory(pageRequest, label)
            LabelType.CREDIT -> courseDAO.getCourseByCredit(pageRequest, label.toInt())
        }
        return convertEntityToVOs(basicInfo)
    }

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
        val A = courseFeedbacks.count { it.grade == Grade.A }
        val B = courseFeedbacks.count { it.grade == Grade.B }
        val C = courseFeedbacks.count { it.grade == Grade.C }
        val D = courseFeedbacks.count { it.grade == Grade.D }
        val E = courseFeedbacks.count { it.grade == Grade.E }
        return mapOf(
                Pair("A", A), Pair("B", B), Pair("C", C), Pair("D", D), Pair("E", E)
        )
    }

    private fun genCheckInIndexes(courseFeedbacks: List<CourseFeedback>) = courseFeedbacks.
            groupBy { it.checkInFrequency.toString() }.mapValues { it.value.size }

    override fun getCourseDetailVO(id: Int): CourseDetailVO {
        val course = courseDAO.getCourseById(id).orElse(null) ?: throw RuntimeException("Error")
        val courseFeedbacks = courseFeedbackRepository.findAllByCourseId(id)
        val rate = courseFeedbacks.map { it.rate }.average()
        val pressureIndexes = genPressureIndexes(courseFeedbacks)
        val examineIndexes = genExamineIndexes(courseFeedbacks)
        val gradeIndexes = genGradeIndexes(courseFeedbacks)
        val checkInIndexes = genCheckInIndexes(courseFeedbacks)

        return CourseDetailVO(course, rate, false, examineIndexes,
                pressureIndexes, gradeIndexes, checkInIndexes)
    }
}