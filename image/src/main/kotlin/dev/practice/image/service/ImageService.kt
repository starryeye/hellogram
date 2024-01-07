package dev.practice.image.service

import dev.practice.image.controller.Image
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.flux
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ImageService(
    private val redisTemplate: ReactiveStringRedisTemplate
) {

    /**
     * service 는 coroutine 으로 구성된다.
     */

    suspend fun findImageById(imageId: Long): Image? {

        // kotlin 을 사용해서 동기 처럼 보이는 코드로 작성할 수 있다.

        val raw = redisTemplate.opsForValue()
            .get(imageId.toString())
            .awaitSingleOrNull()
            ?: return null

        return toImage(raw)
    }

    fun findImagesByIds(imageIds: List<Long>): Flux<Image> {

        return flux {

            val images = redisTemplate.opsForValue()
                .multiGet(imageIds.map { it.toString() })
                .awaitSingle()

            images.filterNotNull()
                .map { toImage(it) }
                .forEach { this.send(it) }
        }
    }

    /**
     * redisTemplate opsForValue 로... id: id,url,width,height
     *
     * test 시..
     * "set 1 1,http://practice.dev/images/1,200,200" 을 셋팅 해두고 하면 get 동작함
     */
    private fun toImage(raw: String): Image {

        val(id, url, width, height) = raw.split(",")

        return Image(id.toLong(), url, width.toInt(), height.toInt())
    }
}