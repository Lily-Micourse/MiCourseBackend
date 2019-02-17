package org.lily.micourse.vo.course

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

data class CourseCommetVotingStateVO (

        val comments: Map<Int, Int>,

        val subComments: Map<Int, Int>
)