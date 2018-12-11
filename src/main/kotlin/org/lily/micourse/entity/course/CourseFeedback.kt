package org.lily.micourse.entity.course

import org.lily.micourse.entity.user.User
import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */
@Entity
@Table
data class CourseFeedback(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
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



