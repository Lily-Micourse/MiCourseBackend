package org.lily.micourse.services.course.impl

import org.lily.micourse.dao.course.CourseFeedbackDao
import org.lily.micourse.dao.course.CourseInfoDao
import org.lily.micourse.entity.course.QueryType
import org.lily.micourse.entity.course.RankingType
import org.lily.micourse.services.course.CourseInfoService
import org.lily.micourse.vo.course.CourseVO
import org.springframework.beans.factory.annotation.Autowired
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
    private lateinit var courseFeedbackDao: CourseFeedbackDao

    override fun getCourseVOListByKey(rankingType: RankingType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCourseVOListByQuery(query: String, queryType: QueryType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}