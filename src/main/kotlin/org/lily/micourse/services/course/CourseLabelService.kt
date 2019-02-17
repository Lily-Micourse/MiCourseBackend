package org.lily.micourse.services.course

import org.lily.micourse.vo.course.LabelListVO

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

interface CourseLabelService {

    fun getCourseLabels(): LabelListVO

}