package com.example.sampleapi.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class User constructor(
  var userId: String,
  var userPassword: String,

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,
) {
  init {
    if (userId.isBlank()) {
      throw IllegalArgumentException("아이디는 비어 있을 수 없습니다.")
    }

    if (userPassword.isBlank()) {
      throw IllegalArgumentException("비밀번호는 비어 있을 수 없습니다.")
    }
  }
}