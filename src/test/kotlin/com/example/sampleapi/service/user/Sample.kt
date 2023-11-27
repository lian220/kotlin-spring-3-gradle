package com.example.sampleapi.service.user

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Size
import java.time.Instant


data class Sample(
  @field:NotNull
  val id: Long? = null,

  @field:NotBlank
  val orderNo: String? = null,

  @field:Size(min = 2, max = 10)
  val productName: String,

  @field:Min(1)
  @field:Max(100)
  val quantity: Int = 0,

  @field:Min(0)
  val price: Long = 0,

  @field:Size(max = 3)
  val items: List<String> = ArrayList(),

  @field:PastOrPresent
  val orderedAt: Instant? = null
) {
  // Default constructor if needed

}