package com.example.Tabla2.repository

import com.example.Tabla2.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long> {
    fun findById (id : Long?) : Client?
}
