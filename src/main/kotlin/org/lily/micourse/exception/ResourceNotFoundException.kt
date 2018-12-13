package org.lily.micourse.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Author: J.D. Liao
 * Date: 2018/12/13
 * Description:
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(message: String = "Not Found") : RuntimeException(message)

fun userNotFoundException(id: String) = ResourceNotFoundException("User $id not found")