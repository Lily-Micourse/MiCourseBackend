package org.lily.micourse.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Author: J.D. Liao
 * Date: 2018/12/14
 * Description:
 */


val IMAGE_DIR = initializeAvatarDirectory()

internal fun initializeAvatarDirectory() = ""

@Component
class ImageConfig {

    @Value("\${app.dir.avatar:static/image/avatar}")
    private lateinit var avatarDirPath: String


}

@ConfigurationProperties(prefix = "file")
class FileStorageProperties {

    lateinit var avatarDir: String
}