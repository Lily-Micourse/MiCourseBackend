package org.lily.micourse.po.course

import org.lily.micourse.po.user.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Entity
@EntityListeners(value = [(AuditingEntityListener::class)])
@Table(name = "course_sub_comment")
data class CourseSubComment (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = -1,

        @ManyToOne(targetEntity = CourseComment::class , fetch = FetchType.EAGER)
        @JoinColumn(name = "commentId")
        val courseComment: CourseComment,

        val replyToSubCommentId: Int?, // 这边不做成外键了

        var deleted: Boolean = false,

        val userId: Int,

        val content: String,

        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        val addTime: Date = Date()

)