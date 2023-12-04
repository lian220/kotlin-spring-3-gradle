package com.example.sampleapi.service.user

import com.example.sampleapi.domain.user.User
import com.example.sampleapi.dto.user.PasswordStrength

interface UserService {
  fun isDuplicatedUserId(id: String): Boolean
  fun validatePassword(password: String): Boolean
  fun assertPasswordStrength(passedCount: Int): PasswordStrength
  fun createUser(user: User): User
}