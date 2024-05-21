package com.example.Tabla2.repository

import com.example.Tabla2.entity.Invoice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<Invoice, Long?> {
    fun findById(id: Long?): Invoice?
}