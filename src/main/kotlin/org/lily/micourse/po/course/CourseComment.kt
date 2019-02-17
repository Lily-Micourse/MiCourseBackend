package org.lily.micourse.po.course

import org.lily.micourse.po.user.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */
@Entity
@EntityListeners(value = [(AuditingEntityListener::class)])
@Table(name = "course_comment")
data class CourseComment (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = -1,

        val courseId: Int,

        val userId: Int,

        var deleted: Boolean = false,

        val content: String,

        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        val addTime: Date = Date(),

        val semester: String, // 我觉得偶尔偷懒也没什么
        // 偷懒是不会被发现的！嗯！

        // 每个评论和它的子评论有一个双向关联关系
        @OneToMany(targetEntity = CourseSubComment::class, cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "courseComment")
        val subComments: Set<CourseSubComment>
)


