package org.lily.micourse.entity.course

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
enum class CheckInFrequency(private val frontEndName: String) {
    NEVER("never"),
    ONCE("once"),
    TWICE("twice"),
    LESS_THAN_FIVE("lessThanFive"),
    MORE_THAN_FIVE("moreThanFive");

    override fun toString(): String = frontEndName
}