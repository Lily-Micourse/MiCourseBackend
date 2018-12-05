package org.lily.micourse.dao.course

import org.lily.micourse.entity.course.Course
import org.lily.micourse.services.course.CourseRankingType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.util.stream.Collectors

/**
 * Created on 05/12/2018.
 * Description:
 * @author iznauy
 */


class CourseDao (

        @Autowired
        private var courseRepository: CourseRepository) {

    /**
     * 获取分页
     */
    private fun generatePageRequest(pageId: Int = 0, pageSize: Int = 20,
                                                   rankingType: CourseRankingType = CourseRankingType.HOT)
            = when(rankingType) {
        CourseRankingType.HOT -> PageRequest.of(pageId, pageSize,
                Sort(Sort.Direction.DESC, "commentAmount"))
        CourseRankingType.LAST -> PageRequest.of(pageId, pageSize,
                Sort.by(Sort.Direction.DESC, "id"))
        CourseRankingType.RECOMMENDED -> PageRequest.of(pageId, pageSize,
                Sort.by(Sort.Direction.DESC, "id"))
    }


    fun getCourseListByKeyWord(pageRequest: PageRequest, pattern: String?): List<Course> =
         if (pattern == null)
            courseRepository.findAll(pageRequest).content
        else
            courseRepository.findByCourseNameLike("%$pattern%", pageRequest).content

    fun getCourseListByCredit(pageRequest: PageRequest, credit: Int): List<Course> =
            courseRepository.findByCredit(credit, pageRequest).content

//    fun getCourseListByCourseTypeLabel(pageRequest: PageRequest, typeLabel: String): List<Course> {
//
//    }

    fun getCreditLabels(): List<String> =
            courseRepository.findDistinctCredits().stream().map { it.toString() }.collect(Collectors.toList())

}