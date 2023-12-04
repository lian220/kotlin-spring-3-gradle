package com.example.sampleapi.service.user

import com.example.sampleapi.domain.user.User
import com.example.sampleapi.domain.user.UserRepository
import com.example.sampleapi.dto.user.PasswordStrength
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
  private val userRepository: UserRepository,
) : UserService {

  override fun isDuplicatedUserId(id: String): Boolean {
    return userRepository.findByUserId(id)?.let { false } ?: true
  }

  override fun validatePassword(password: String): Boolean {
    var passedCount = 0
    //비밀번호는 최소 9자 이상 15자 이하
    if (9 <= password.length && 15 > password.length) passedCount++

    //비밀번호 대문자 1글자 이상
    if (isCheckAlphabetUpperCase(password)) passedCount++

    if (password.contains('!') || password.contains('@') || password.contains('#')) passedCount++

    if(assertPasswordStrength(passedCount) == PasswordStrength.STRONG) return true
    else return false
  }

  private fun isCheckAlphabetUpperCase(password: String): Boolean {
    var isAlphaUpperCase = false
    password.toMutableList().forEach({
      if (it >= 'A' && it <= 'Z') {
        isAlphaUpperCase = true
      }
    })
    return isAlphaUpperCase
  }

  override fun assertPasswordStrength(passedCount: Int): PasswordStrength {
    if(passedCount == 1) return PasswordStrength.WEEK
    else if (passedCount == 2) return PasswordStrength.NORMAL
    else if(passedCount == 3) return PasswordStrength.STRONG
    else return PasswordStrength.WEEK
  }

  override fun createUser(user: User): User {
    return userRepository.save(user)
  }
}