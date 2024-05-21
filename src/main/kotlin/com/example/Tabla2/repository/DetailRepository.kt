package com.example.Tabla2.repository

import com.example.Tabla2.entity.Detail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DetailRepository : JpaRepository<Detail, Long?>{
    fun findById (id: Long?): Detail?

}
