package com.example.sampleapi.service.user

data class SignOnCase(
    var userId: String,
    var password: String,
    var result: Boolean
)
