package org.lily.micourse.entity.course

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
enum class CoursePressure(private val frontEndName: String) {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    override fun toString(): String = frontEndName
}