package org.lily.micourse.entity.course

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Table

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */


@Table
@Entity
data class CourseTagSet (

        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        val tag: String
)

@Table
@Entity
data class CourseTag (

        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        val courseId: Int,

        val tagId: Int

)