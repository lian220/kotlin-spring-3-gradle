package com.example.sampleapi.dto.cart

enum class DibsKeyTypeS(private val keyComponent: String) {
  ITEM("item"),
  BRAND("brand"),
  SELLER("seller");

  fun getDibsKey(userId: String) = "dibs:$userId:$keyComponent"
  fun getDibsPendingDataKey() = "dibs:$keyComponent:pending"
}