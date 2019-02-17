package org.lily.micourse.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created on 17/02/2019.
 * Description:
 * @author iznauy
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class UnexpectedParamValueException(msg: String = "unexpected param value"): RuntimeException(msg)