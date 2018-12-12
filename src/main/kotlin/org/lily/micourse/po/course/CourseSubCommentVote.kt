package org.lily.micourse.po.course

import javax.persistence.*

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Entity
@Table
data class CourseSubCommentVote (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int = -1,

        val userId: Int = -1,

        val subCommentId: Int = -1

)