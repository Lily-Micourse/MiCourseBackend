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
data class Course (
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        val courseIcon: String,

        val courseCode: String,

        val courseName: String,

        val credit: Int,

        val teacher: String,

        val courseCategoryId: Int,

        val courseDepartmentId: Int,

        val description: String
)