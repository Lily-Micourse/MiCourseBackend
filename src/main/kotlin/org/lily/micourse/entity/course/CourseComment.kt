package org.lily.micourse.entity.course

import org.lily.micourse.entity.user.User
import java.sql.Timestamp
import javax.persistence.*

/**
 * Created on 11/11/2018.
 * Description:
 * @author iznauy
 */
@Entity
@Table(name = "course_comment")
data class CourseComment (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = -1,

        @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY) // 一般用不到课程的信息
        @JoinColumn(name = "courseId")
        val course: Course,

        @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.EAGER) // 一般取出评论的话，需要获取用户什么的头像，最好还是取出用户
        @JoinColumn(name = "userId")
        val user: User,

        var deleted: Boolean,

        val content: String,

        @Temporal(TemporalType.TIMESTAMP)
        val addTime: Timestamp,

        val semester: String, // 我觉得偶尔偷懒也没什么
        // 偷懒是不会被发现的！嗯！

        // 每个评论和它的子评论有一个双向关联关系
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "course_comment")
        val subComments: Set<CourseSubComment>
)


