package org.lily.micourse.po.course

import org.lily.micourse.po.user.User
import java.sql.Timestamp
import javax.persistence.*

/**
 * Created on 11/12/2018.
 * Description:
 * @author iznauy
 */
@Entity
@Table(name = "course_sub_comment")
data class CourseSubComment (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        @OneToMany(fetch = FetchType.EAGER)
        @JoinColumn(name = "commentId")
        val courseComment: CourseComment,

        val replyToSubCommentId: Int, // 这边不做成外键了

        var deleted: Boolean,

        @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.EAGER) // 一般取出评论的话，需要获取用户什么的头像，最好还是取出用户
        @JoinColumn(name = "userId")
        val user: User,

        val content: String,

        @Temporal(TemporalType.TIMESTAMP)
        val addTime: Timestamp

)