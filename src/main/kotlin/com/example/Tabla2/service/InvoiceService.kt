package com.example.Tabla2.service

import com.example.Tabla2.entity.Invoice
import com.example.Tabla2.entity.InvoiceView
import com.example.Tabla2.repository.InvoiceRepository
import com.example.Tabla2.repository.InvoiceViewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class InvoiceService {

    @Autowired
    lateinit var invoiceViewRepository: InvoiceViewRepository

    @Autowired
    lateinit var invoiceRepository: InvoiceRepository

    fun list(): List<Invoice> {
        return invoiceRepository.findAll()
    }

    fun listView(): List<InvoiceView> {
        return invoiceViewRepository.findAll()
    }

    fun getTotal(value: Double): List<Invoice> {
        return invoiceRepository.findTotal(value)
    }

    fun save(invoice: Invoice): Invoice {
        if (!isValidInvoiceCode(invoice.code)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Invoice Code")
        }
        return invoiceRepository.save(invoice)
    }

    fun update(invoice: Invoice): Invoice {
        try {
            invoiceRepository.findById(invoice.id)
                ?: throw Exception("Invoice ID does not exist")
            if (!isValidInvoiceCode(invoice.code)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Invoice Code")
            }
            return invoiceRepository.save(invoice)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun updateName(invoice: Invoice): Invoice? {
        try {
            val response = invoiceRepository.findById(invoice.id)
                ?: throw Exception("Invoice ID does not exist")
            response.apply {
                code = invoice.code
            }
            if (!isValidInvoiceCode(response.code)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Invoice Code")
            }
            return invoiceRepository.save(response)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun isValidInvoiceCode(code: String?): Boolean {
        return code != null && code.matches(Regex("\\d{3}-\\d{3}-\\d{9}"))
    }
}
