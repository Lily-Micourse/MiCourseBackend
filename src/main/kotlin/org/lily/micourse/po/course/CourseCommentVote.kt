package org.lily.micourse.po.course

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

        val userId: Int,

        val commentId: Int

)