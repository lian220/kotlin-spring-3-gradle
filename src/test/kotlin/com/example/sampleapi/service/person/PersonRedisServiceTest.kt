package com.example.sampleapi.service.person

import com.example.sampleapi.domain.user.Member
import com.example.sampleapi.domain.user.MemberRedisRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class PersonRedisServiceTest {
  @Autowired
  private val repo: MemberRedisRepository? = null

  @Test
  fun redisTest() {
    val person = Member("mustit", "lian")

    // 저장
    repo!!.save(person)

    // `keyspace:id` 값을 가져옴
    person.id?.let { repo!!.findById(it) }

    // Person Entity 의 @RedisHash 에 정의되어 있는 keyspace (people) 에 속한 키의 갯수를 구함
    repo!!.count()

    // 삭제
    repo!!.delete(person)
  }
}