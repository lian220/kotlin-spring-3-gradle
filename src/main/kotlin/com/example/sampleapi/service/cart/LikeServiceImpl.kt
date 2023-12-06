package com.example.sampleapi.service.cart

import com.example.sampleapi.domain.user.Member
import com.example.sampleapi.domain.user.MemberRedisRepository
import com.example.sampleapi.dto.cart.DibsKeyTypeS
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class LikeServiceImpl(
  private val webCacheTransactionalRedisTemplate: RedisTemplate<String, Any>,
  private val repo: MemberRedisRepository

) : LikeService {

  @Transactional
  override fun addLikeItem(): ItemLikeResultS  {
    val (userId, itemNo) = ItemLikeReqI("lian", 1)

    requireNotNull(userId) { throw Exception("userId must be not null") }
    requireNotNull(itemNo) { throw Exception("itemNo must be not null") }

    try {
      webCacheTransactionalRedisTemplate.opsForZSet().add(
        DibsKeyTypeS.ITEM.getDibsKey(userId), itemNo,
        Instant.now().epochSecond.toDouble()
      )
      webCacheTransactionalRedisTemplate.opsForHash<String, String>()
        .put(DibsKeyTypeS.ITEM.getDibsPendingDataKey(), userId + "_" + itemNo, "Y")
    } catch (e: Exception) {
      throw Exception("Redis add operation failed")
    }

    return ItemLikeResultS(
      userId = userId,
      itemNo = itemNo
    )
  }

  override fun addPerson() {
    val person = Member("mustit2", "lian", "dylim@mustit.co.kr", 18)

    // 저장
    repo!!.save(person)

    // `keyspace:id` 값을 가져옴
    var member = person.id?.let { repo!!.findById(it) }

    // Person Entity 의 @RedisHash 에 정의되어 있는 keyspace (people) 에 속한 키의 갯수를 구함
    repo!!.count()
  }
}

data class ItemLikeReqI(
  val userId: String? = null,
  val itemNo: Long? = null
)

data class ItemLikeResultS(
  var userId: String,
  var itemNo: Long
)