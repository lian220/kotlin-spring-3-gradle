package com.example.sampleapi.controller

import com.example.sampleapi.service.cart.LikeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonController @Autowired constructor(
  private val likeService: LikeService
) {
  @GetMapping("/{id}")
  fun getUser(@PathVariable id: String) {
    likeService.addPerson()
  }

}