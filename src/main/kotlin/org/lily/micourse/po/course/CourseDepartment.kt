package org.lily.micourse.po.course

import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */
@Entity
@Table
data class CourseDepartment(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = -1,

        val name: String = ""
)