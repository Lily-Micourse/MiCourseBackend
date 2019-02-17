package org.lily.micourse.controller.course

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.lily.micourse.entity.course.QueryType
import org.lily.micourse.entity.course.RankingType
import org.lily.micourse.services.course.CourseDetailInfoService
import org.lily.micourse.services.course.CourseInfoService
import org.lily.micourse.services.course.CourseLabelService
import org.lily.micourse.vo.course.CourseVO
import org.lily.micourse.vo.course.LabelListVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */

@RestController
class CourseInfoController {

    @Autowired
    private lateinit var courseInfoService: CourseInfoService

    @Autowired
    private lateinit var courseLabelService: CourseLabelService

    @Autowired
    private lateinit var courseDetailInfoService: CourseDetailInfoService

    @GetMapping("/course")
    @ApiOperation(value = "get course list")
    fun getCourseList(@RequestBody(required = false) type: RankingType?,@RequestBody(required = false) query: String?,
                      @RequestBody(required = false) queryType: QueryType?, @RequestBody(required = false) page: Int?,
                      @RequestBody(required = false) pageSize: Int?,
                      @ApiIgnore httpRequest: HttpServletRequest, @ApiIgnore httpResponse: HttpServletResponse)
            : List<CourseVO> {
        val actPage = page ?: 0
        val actPageSize = pageSize ?: 30
        val result = when {
            type != null -> courseInfoService.getCourseVOListByKey(type, actPage, actPageSize)
            else -> courseInfoService.getCourseVOListByQuery(query!!, queryType!!, actPage, actPageSize)
        }
        httpResponse.setHeader("total-count", result.second.toString())
        return result.first
    }

    @GetMapping("/label")
    @ApiOperation(value = "get label list")
    fun getLabels(): LabelListVO = courseLabelService.getCourseLabels()

    @GetMapping("/courseInfo")
    @ApiOperation(value = "get course detail information")
    fun getCourseDetailInfo(courseId: String) = courseDetailInfoService.getCourseDetailVO(courseId.toInt())

}