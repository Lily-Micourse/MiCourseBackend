package org.lily.micourse.dao

import org.lily.micourse.po.SchoolDepartment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Author: J.D. Liao
 * Date: 2018/12/13
 * Description:
 */

interface SchoolDepartmentRepository: JpaRepository<SchoolDepartment, Int> {

    @Query("select s.name from SchoolDepartment s where s.id = ?1")
    fun getNameById(id: Int): String?

    @Query("select s.id from SchoolDepartment s where s.name = ?1")
    fun getIdByName(name: String): Int?
}