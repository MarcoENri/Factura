package com.example.Tabla2.repository

import com.example.Tabla2.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, String> {
    fun findByUsername(username: String): User
}
