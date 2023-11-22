package com.example.sampleapi.controller

import com.example.sampleapi.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(
    private val userService: UserService
) {
  @GetMapping("/{id}")
  fun getUser(@PathVariable id: String) {
    userService.isDuplicatedUserId(id)
  }
}