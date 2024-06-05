package com.example.Tabla2.repository

import com.example.Tabla2.entity.InvoiceView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceViewRepository : JpaRepository<InvoiceView, Long?> {
    fun findById(id: Long?): InvoiceView?


}
