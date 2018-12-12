package org.lily.micourse.po.course

import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */

@Entity
@Table
data class Course (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = -1,

        val courseIcon: String,

        val courseCode: String,

        val courseName: String,

        val credit: Int,

        val teacher: String,

        @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.EAGER)
        @JoinColumn(name = "courseCategoryId")
        val courseCategory: CourseCategory,

        @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.EAGER)
        @JoinColumn(name = "courseDepartmentId")
        val courseDepartment: CourseDepartment,

        val description: String

)