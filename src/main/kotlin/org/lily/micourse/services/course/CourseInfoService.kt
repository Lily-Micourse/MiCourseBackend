package org.lily.micourse.services.course

import org.lily.micourse.vo.course.CourseDetailVO
import org.lily.micourse.vo.course.CourseVO
import org.lily.micourse.vo.course.LabelListVO

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
interface CourseInfoService {

    fun getTermList(courseId: Int): List<String>

    fun getLabelList(): LabelListVO

    fun getCourseList(pattern: String, rankingType: CourseRankingType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long>

    fun getCourseListByLabel(label: String, labelType: LabelType, page: Int, pageSize: Int): Pair<List<CourseVO>, Long>

    fun getCourseDetailVO(id: Int): CourseDetailVO

}