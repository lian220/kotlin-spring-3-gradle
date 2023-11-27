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
    return userRepository.findByUserId(id)?.let { false } ?: let { true }
  }

  override fun validatePassword(password: String): Boolean {
    //비밀번호는 최소 9자 이상 15자 이하
    if (9 >= password.length && 15 < password.length) {
      return false
    }

    //비밀번호 대문자 1글자 이상
    if (!isCheckAlphabetUpperCase(password)) {
      return false
    }

    if (!(password.contains('!') || password.contains('@') || password.contains('#'))) {
      return false
    }
    //숫자를 포함
    return true
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

  override fun assertPasswordStrength(passwordStrength: PasswordStrength): Boolean {
    if(PasswordStrength.STRONG == passwordStrength) {
      return true
    }
    return false
  }

  override fun createUser(user: User): User {
    return userRepository.save(user)
  }
}