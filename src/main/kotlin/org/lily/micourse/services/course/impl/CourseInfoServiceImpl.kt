package org.lily.micourse.services.course.impl

import org.lily.micourse.dao.course.CourseCommentDao
import org.lily.micourse.dao.course.CourseFeedbackDao
import org.lily.micourse.dao.course.CourseInfoDao
import org.lily.micourse.entity.course.QueryType
import org.lily.micourse.entity.course.RankingType
import org.lily.micourse.exception.UnexpectedParamValueException
import org.lily.micourse.po.course.Course
import org.lily.micourse.services.course.CourseInfoService
import org.lily.micourse.vo.course.CourseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

@Service
class CourseInfoServiceImpl: CourseInfoService {

    @Autowired
    private lateinit var courseInfoDao: CourseInfoDao

    @Autowired
    private lateinit var courseCommentDao: CourseCommentDao

    @Autowired
    private lateinit var courseFeedbackDao: CourseFeedbackDao

    private fun convertEntityToVOs(basicInfo: Pair<List<Course>, Long>) : Pair<List<CourseVO>, Long> {
        val courseList = basicInfo.first
        val totalCourse = basicInfo.second

        val courseIds = courseList.map { it.id }

        val courseScores = courseFeedbackDao.findCourseRatesByIds(courseIds)
        val courseCommentCounts = courseCommentDao.getCommentCountByCourseIds(courseIds)

        val resultList: MutableList<CourseVO> = mutableListOf()
        courseList.forEach { it -> resultList.add(CourseVO(it,
                courseScores[it.id] ?: throw UnexpectedParamValueException(),
                courseCommentCounts[it.id] ?: throw UnexpectedParamValueException())) }
        return Pair(resultList, totalCourse)
    }

    override fun getCourseVOListByKey(rankingType: RankingType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long> {
        val sort: Sort =
                when(rankingType) {
                    RankingType.HOT -> Sort.by(Sort.Direction.DESC, "credit")
                    RankingType.RECOMMENDED -> Sort.by(Sort.Direction.DESC, "credit")
                    RankingType.LAST -> Sort.by(Sort.Direction.DESC, "id")
                }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        val rawCourses = courseInfoDao.getCourseList(pageRequest)
        return convertEntityToVOs(rawCourses)
    }

    override fun getCourseVOListByQuery(query: String, queryType: QueryType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long> {
        val pageRequest: PageRequest = PageRequest.of(page, pageSize)
        val rawCourses = when(queryType) {
            QueryType.STRING -> courseInfoDao.getCourseList(pageRequest, query)
            QueryType.CATEGORY -> courseInfoDao.getCourseAndTotalCountByCategory(pageRequest, query)
            QueryType.CREDIT -> courseInfoDao.getCourseAndTotalCountByCredit(pageRequest, query.toInt())
            QueryType.DEPARTMENT -> courseInfoDao.getCourseAndTotalCountByDepartment(pageRequest, query)
        }
        return convertEntityToVOs(rawCourses)
    }
}