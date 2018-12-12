package org.lily.micourse.entity.course

import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */


@Table
@Entity
@Deprecated(message = "必须人工参与")
data class CourseTagSet (

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        val id: Int,

        val tag: String
)

@Table
@Entity
@Deprecated(message = "必须人工参与")
data class CourseTag (

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        val id: Int,

        val courseId: Int,

        val tagId: Int

)