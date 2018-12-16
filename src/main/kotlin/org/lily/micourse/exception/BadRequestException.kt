package org.lily.micourse.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

/**
 * Author: J.D. Liao
 * Date: 2018/12/16
 * Description:
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(message: String = ""): RuntimeException(message)