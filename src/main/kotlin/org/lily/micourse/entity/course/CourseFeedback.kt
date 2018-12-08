package org.lily.micourse.entity.course

import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */

@Entity
@Table
data class CourseFeedback(

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        val id: Int,

        val courseId: Int,

        val userId: Int,

        val rate: Int,

        @Enumerated(value = EnumType.ORDINAL)
        val pressure: CoursePressure,

        val score: Int,

        val evalPaper: Boolean,

        val evalAttendance: Boolean,

        val evalTeamWork: Boolean,

        val evalClosedBookExam: Boolean,

        val evalOpenBookExam: Boolean,

        val evalOthers: Boolean,

        @Enumerated(value = EnumType.ORDINAL)
        val checkInFrequency: CheckInFrequency
)

enum class CoursePressure(private val frontEndName: String) {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    override fun toString(): String = frontEndName
}

enum class CheckInFrequency(private val frontEndName: String) {
    NEVER("never"),
    ONCE("once"),
    TWICE("twice"),
    LESS_THAN_FIVE("lessThanFive"),
    MORE_THAN_FIVE("moreThanFive");

    override fun toString(): String = frontEndName
}