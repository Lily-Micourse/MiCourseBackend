package org.lily.micourse.services.course

import org.lily.micourse.vo.course.CourseDetailVO

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
interface CourseDetailInfoService {

    fun getCourseDetailVO(id: Int): CourseDetailVO

}