package org.lily.micourse.exception

/**
 * Author: J.D. Liao
 * Date: 2018/12/14
 * Description:
 */

const val OLD_PASSWORD_WRONG_MESSAGE = "旧密码错误"

class OldPasswordException(message: String = OLD_PASSWORD_WRONG_MESSAGE) : RuntimeException(message)