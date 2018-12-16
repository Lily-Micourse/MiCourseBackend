package org.lily.micourse.services

import org.lily.micourse.config.FileStorageProperties
import org.lily.micourse.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * Author: J.D. Liao
 * Date: 2018/12/16
 * Description:
 */

private const val CREATE_DIR_FAILED_MSG = "Create directory failed"

private val logger = LoggerFactory.getLogger("FileStorage")

@Service
class FileStorageService(fileStorageProperties: FileStorageProperties) {

    private final val avatarStorageLocation: Path

    init {
        // Get path
        val classPath = ResourceUtils.getURL("classpath:").path
        avatarStorageLocation = Paths.get(classPath, fileStorageProperties.avatarDir).toAbsolutePath().normalize()
        // Create the directory
        try {
            Files.createDirectories(avatarStorageLocation)
        } catch (e: IOException) {
            logger.error(CREATE_DIR_FAILED_MSG)
            throw IllegalStateException(CREATE_DIR_FAILED_MSG)
        }
    }

    fun storeAvatar(file: MultipartFile, newName: String = ""): String {
        // If new name is blank, then check the original file name and use it if it's valid
        var finalNewName = newName

        if (newName.isBlank()) {
            val fileName = StringUtils.cleanPath(file.originalFilename!!)
            // Check file name
            if (fileName.contains(".."))
                throw BadRequestException("Sorry! Filename $fileName contains invalid path sequence")

            finalNewName = fileName
        }

        // Save the file to target location
        val targetLocation = avatarStorageLocation.resolve(finalNewName)
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

        logger.info(targetLocation.toString())
        logger.info(avatarStorageLocation.toString())
        logger.info(avatarStorageLocation.parent.toString())

        // result should be like /avatar/1.png
        return avatarStorageLocation.parent.relativize(targetLocation).normalize().toString()
    }
}