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
@Entity
@Table
data class CourseCategory (

        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        val name: String
)