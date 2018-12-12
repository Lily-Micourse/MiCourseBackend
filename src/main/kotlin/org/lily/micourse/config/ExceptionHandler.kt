package org.lily.micourse.config

import org.lily.micourse.entity.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Author: J.D. Liao
 * Date: 2018/12/12
 * Description:
 */

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(LockedException::class)
    fun handleLockedException(e: LockedException, request: WebRequest) =
        ResponseEntity(ErrorMessage("frozen"), HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(e: BadCredentialsException, request: WebRequest) =
        ResponseEntity(ErrorMessage("incorrect"), HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(DisabledException::class)
    fun handleDisabledException(e: DisabledException, request: WebRequest) =
        ResponseEntity(ErrorMessage("notverified"), HttpStatus.UNAUTHORIZED)
}