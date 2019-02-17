package org.lily.micourse.dao.course

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
interface CourseLabelDao {

    fun getCreditLabels(): List<String>

    fun getDepartmentLabels(): List<String>

    fun getCategoryLabels(): List<String>

}