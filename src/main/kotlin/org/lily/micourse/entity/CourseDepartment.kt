package org.lily.micourse.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Author: J.D. Liao
 * Date: 2018/11/11
 * Description:
 */

@Entity
data class CourseDepartment(
        val name: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = -1
)