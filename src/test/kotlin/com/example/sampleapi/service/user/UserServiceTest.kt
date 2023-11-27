package com.example.sampleapi.service.user

import com.example.sampleapi.domain.user.User
import com.example.sampleapi.domain.user.UserRepository
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UserServiceAnnotationTest {
}

class SignOnAnnotationTest1 : AnnotationSpec() {
  val userRepository = mockk<UserRepository>()
  val userService = UserServiceImpl(userRepository)


  @Test
  fun `id 중복이 안됫을때`() {
    every { userRepository.findByUserId("lian") } returns User("lian", "test!123aA")
    every { userRepository.findByUserId("test") } returns null
    var userId = "test"
    userService.isDuplicatedUserId("test") shouldBe false
  }

  @Test
  fun `아이디 중복이 됬을때`() {
    every { userRepository.findByUserId("lian") } returns User("lian", "test!123aA")
    every { userRepository.findByUserId("test") } returns null
    var userId = "lian"
    userService.isDuplicatedUserId("lian") shouldBe true
  }

}

class SignOnBehaviorTest1 : BehaviorSpec({
  val userRepository = mockk<UserRepository>()
  val userService = UserServiceImpl(userRepository)

  every { userRepository.findByUserId("test") } returns User("test", "test123")
  every { userRepository.findByUserId("lian") } returns null

  given("회원가입 정보 받았을때") {
    `when`("이미 존재하지 않는 id 일때") {
      then("이미 회원가입이 되어 있습니다.") {
        userService.isDuplicatedUserId("test") shouldBe true
      }
    }

    `when`("존재하지 않는 id 일때") {
      then("이미 회원가입이 되어 있습니다.") {
        userService.isDuplicatedUserId("lian") shouldBe false
      }
    }

    `when`("빈 값을 받았을때") {
      var passwordCase1 = ""
      userService.validatePassword(passwordCase1) shouldBe false
    }

    `when`("9자 이하일때") {
      var passwordCase2 = "12312311"
      userService.validatePassword(passwordCase2) shouldBe false
    }

    `when`("15자 초과 일때") {
      var passwordCase3 = "112233445566778"
      userService.validatePassword(passwordCase3) shouldBe false
    }

    `when`("알파벳 대문자가 없을때") {
      var passwordCase4 = "12312311"
      userService.validatePassword(passwordCase4) shouldBe false
    }

    `when`("알파벳 대문자가 있을때") {
      var passwordCase5 = "123123A1"
      userService.validatePassword(passwordCase5) shouldBe false
    }

    `when`("특수문자가 없을때") {
      var passwordCase6 = "123123A1"
      userService.validatePassword(passwordCase6) shouldBe false
    }

    `when`("특수문자가 있을때") {
      var passwordCase7 = "123123Ad!"
      userService.validatePassword(passwordCase7) shouldBe false
    }
  }


})