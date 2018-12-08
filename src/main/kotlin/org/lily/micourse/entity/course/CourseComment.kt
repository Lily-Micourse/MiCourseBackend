package org.lily.micourse.entity.course

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */
@Entity
@Table
data class CourseComment (

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        val id: Int,

        val courseId: Int,

        val userId: Int,

        var deleted: Boolean,

        var useful: Int,

        var useless: Int,

        val content: String,

        @Temporal(TemporalType.TIMESTAMP)
        val addTime: Date,

        val semester: String

)

@Entity
@Table
data class CourseSubComment (

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        val id: Int,

        val commentId: Int,

        val replyTo: Int,

        var deleted: Boolean,

        var useful: Int,

        val useless: Int,

        val content: String,

        @Temporal(TemporalType.TIMESTAMP)
        val addTime: Date

)