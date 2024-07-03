package com.example.Tabla2.service

import com.example.Tabla2.entity.Product
import com.example.Tabla2.repository.ProductRepository
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    lateinit var productService: ProductService

    @Mock
    lateinit var productRepository: ProductRepository

    val jsonString = File("./src/test/resources/product/newProduct.json").readText(Charsets.UTF_8)
    val productMock = Gson().fromJson(jsonString, Product::class.java)

    @Test
    fun saveProductCorrect() {
        Mockito.`when`(productRepository.save(Mockito.any(Product::class.java))).thenReturn(productMock)
        val response = productService.save(productMock)
        Assertions.assertEquals(response.id, productMock.id)
    }

    @Test
    fun isValidStock() {
        Assertions.assertTrue(productService.isValidStock(10))
        Assertions.assertFalse(productService.isValidStock(0))
        Assertions.assertFalse(productService.isValidStock(-5))
    }
}
