package org.lily.micourse.entity.course

import javax.persistence.*

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Table
@Entity
data class CourseCommentVote (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int = -1,

        val userId: Int = -1,

        val commentId: Int = -1

)