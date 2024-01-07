package dev.practice.image.controller

import dev.practice.image.service.ImageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux

@RequestMapping("/api/v1/images")
@RestController
class ImageController(
    private val imageService: ImageService,
) {

    // imageId 한개로 하나의 image 정보를 얻는다.
    @GetMapping("/{imageId}")
    suspend fun getImageById(
        @PathVariable("imageId") imageId: Long,
    ): Image {

        // suspend 로 반환하므로 바로 반환..
        return imageService.findImageById(imageId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    }

    // imageId 리스트로 여러개의 image 정보를 얻는다.
    @GetMapping
    fun getImagesByIds(
        @RequestParam("imageIds") imageIds: List<Long>,
    ): Flux<Image> { // suspend 로 반환하지 않고 Flux 로 반환한다...

        return imageService.findImagesByIds(imageIds)
    }
}