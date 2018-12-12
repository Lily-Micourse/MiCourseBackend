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

        @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.EAGER) // 一般取出评论的话，需要获取用户什么的头像，最好还是取出用户
        @JoinColumn(name = "userId")
        val user: User,

        val content: String,

        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        val addTime: Date = Date()

)