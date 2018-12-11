package org.lily.micourse.entity.course

import org.lily.micourse.entity.user.User
import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */

data class CourseRate(

        val id: Int,

        val count: Long
)

@Entity
@Table
data class CourseFeedback(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        val id: Int,

        @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY) // 一般用不到课程的信息
        @JoinColumn(name = "courseId")
        val course: Course,

        @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY) // 一般用不到用户信息
        @JoinColumn(name = "userId")
        val user: User,

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