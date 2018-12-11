package org.lily.micourse.po

import javax.persistence.*

/**
 * Author: J.D. Liao
 * Date: 2018/11/11
 * Description:
 */

@Entity
@Table(name = "school_department")
data class SchoolDepartment(
        val name: String,

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = -1
)