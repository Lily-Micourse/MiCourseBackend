package org.lily.micourse.po.course

import org.lily.micourse.entity.course.CheckInFrequency
import org.lily.micourse.entity.course.CoursePressure
import org.lily.micourse.entity.course.Score
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
        val id: Int = -1,

        val courseId: Int,

        val userId: Int,

        val rate: Int,

        @Enumerated(value = EnumType.ORDINAL)
        val pressure: CoursePressure,

        @Enumerated(value = EnumType.ORDINAL)
        val score: Score,

        @Enumerated(value = EnumType.ORDINAL)
        val checkInFrequency: CheckInFrequency,

        val evalPaper: Boolean,

        val evalAttendance: Boolean,

        val evalTeamWork: Boolean,

        val evalClosedBookExam: Boolean,

        val evalOpenBookExam: Boolean,

        val evalOthers: Boolean
)



