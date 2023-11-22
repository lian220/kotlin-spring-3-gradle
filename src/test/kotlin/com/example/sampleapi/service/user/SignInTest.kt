package com.example.sampleapi.service.user

import com.example.sampleapi.domain.user.User
import com.example.sampleapi.domain.user.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

//class SignOnAnnotationTest : AnnotationSpec() {
//  val userService = UserServiceImpl()
//  @Test
//  fun `빈 값을 받았을때`() {
//    var password = ""
//    password.length shouldBe 0
//  }
//
//  @Test
//  fun `8자 이하 거나 15자 이상일때`() {
//    var password1 = "1231231"
//    userService.validatePassword(password1) shouldBe false
//
//    var password2 = "112233445566"
//    userService.validatePassword(password2) shouldBe false
//  }
//
//  @Test
//  fun `대문자를 포함하고 있으면`() {
//    var password1 = "1231221A"
//    userService.validatePassword(password1) shouldBe true
//  }
//
//}

class SignOnTest() : BehaviorSpec({
  var passwordCasies: List<SignOnCase>? = null

  val userRepository = mockk<UserRepository>()
  val userService = UserServiceImpl(userRepository)

  beforeSpec {
    passwordCasies = settingPasswordCase()
  }

  given("회원가입 정보 받았을때") {
    `when`("이미 존재하지 않는 id 일때") {
      every { userRepository.findByUserId("test") } returns User("test", "test123")
      then("이미 회원가입이 되어 있습니다.") {
        userService.isDuplicatedUserId("test") shouldBe false
      }
    }

    `when`("존재하지 않는 id 일때") {
      every { userRepository.findByUserId("lian") } returns null
      then("이미 회원가입이 되어 있습니다.") {
        userService.isDuplicatedUserId("lian") shouldBe true
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
      var passwordCase7 = "123123A!"
      userService.validatePassword(passwordCase7) shouldBe true
    }

    var lianUser = User("lian", "test1234A!")
    every { userRepository.findByUserId("lian") } returns null
    every { userRepository.findByUserId("test") } returns User("test", "test123")
    every { userRepository.save(lianUser) } returns lianUser

    `when`("회원가입 정보를 올바르게 입력 했을때") {

      and("id중복 검사") {
        userService.isDuplicatedUserId(lianUser.userId) shouldBe true
      }

      and("pw 검증") {
        userService.validatePassword(lianUser.userPassword) shouldBe true
      }

      then("회원 가입 성공.") {
        userService.createUser(lianUser) shouldBe lianUser
      }
    }
  }
})

fun settingPasswordCase(): List<SignOnCase> {
  return listOf(
      SignOnCase("test", "", false),
      SignOnCase("lian", "test123A!", false),
      SignOnCase("test", "12312311", false),
      SignOnCase("lian", "test123A!", true),
  )
}