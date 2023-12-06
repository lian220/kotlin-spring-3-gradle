package com.example.sampleapi.domain.user

import org.springframework.data.repository.CrudRepository

interface MemberRedisRepository: CrudRepository<Member, String> {
}