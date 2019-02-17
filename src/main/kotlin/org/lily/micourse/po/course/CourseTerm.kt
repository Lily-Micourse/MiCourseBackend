package org.lily.micourse.po.course

import javax.persistence.*

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Entity
@Table
data class CourseTerm (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int,

        val courseId: Int,

        val term: String
)