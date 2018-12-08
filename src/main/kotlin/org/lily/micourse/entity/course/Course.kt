package org.lily.micourse.entity.course

import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */

@Entity
@Table
data class Course (
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
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