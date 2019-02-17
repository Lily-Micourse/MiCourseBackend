package org.lily.micourse.services.course

import org.lily.micourse.entity.course.QueryType
import org.lily.micourse.entity.course.RankingType
import org.lily.micourse.vo.course.CourseVO


/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
interface CourseInfoService {

    fun getCourseVOListByKey(rankingType: RankingType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long>

    fun getCourseVOListByQuery(query: String, queryType: QueryType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long>

}