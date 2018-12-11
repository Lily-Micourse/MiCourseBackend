package org.lily.micourse.entity.course

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

        @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
        @JoinColumn(name = "courseId")
        val course: Course,

        val term: String
)