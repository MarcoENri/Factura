package com.example.Tabla2.service

import com.example.Tabla2.entity.Invoice
import com.example.Tabla2.repository.InvoiceRepository
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class InvoiceServiceTest {

    @InjectMocks
    lateinit var invoiceService: InvoiceService

    @Mock
    lateinit var invoiceRepository: InvoiceRepository

    val jsonString = File("./src/test/resources/invoice/newInvoice.json").readText(Charsets.UTF_8)
    val invoiceMock = Gson().fromJson(jsonString, Invoice::class.java)

    @Test
    fun saveInvoiceCorrect() {
        Mockito.`when`(invoiceRepository.save(Mockito.any(Invoice::class.java))).thenReturn(invoiceMock)
        val response = invoiceService.save(invoiceMock)
        Assertions.assertEquals(response.id, invoiceMock.id)
    }

    @Test
    fun isValidInvoiceCode() {
        Assertions.assertTrue(invoiceService.isValidInvoiceCode("001-001-000000001"))
        Assertions.assertFalse(invoiceService.isValidInvoiceCode("001-001-00000001"))
        Assertions.assertFalse(invoiceService.isValidInvoiceCode("001001000000001"))
        Assertions.assertFalse(invoiceService.isValidInvoiceCode(null))
    }
}
