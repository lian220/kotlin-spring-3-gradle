package com.example.sampleapi.domain.user

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash


@RedisHash("member")
data class Member (
  @Id
  val id : String? = null,
  val name : String? = null,
  val email: String? = null,
  var age: Int? = null
)