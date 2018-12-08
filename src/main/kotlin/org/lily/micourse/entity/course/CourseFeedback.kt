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

        val level: Int,

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
        val checkInFrequence: CheckInFrequence
)

enum class CoursePressure {
    LOW,
    MEDIUM,
    HIGH
}

enum class CheckInFrequence {
    NEVER,
    ONCE,
    TWICE,
    LESS_THAN_FIVE,
    MORE_THAN_FIVE
}