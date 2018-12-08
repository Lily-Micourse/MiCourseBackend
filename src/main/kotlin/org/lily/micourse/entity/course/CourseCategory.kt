package org.lily.micourse.entity.course

import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */
@Entity
@Table
data class CourseCategory (

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        val id: Int,

        val name: String
)