package com.example.Tabla2.service

import com.example.Tabla2.entity.Product
import com.example.Tabla2.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductService {

    @Autowired
    lateinit var productRepository: ProductRepository

    fun list(): List<Product> {
        return productRepository.findAll()
    }

    fun save(product: Product): Product {
        if (!isValidStock(product.stock)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Stock")
        }
        return productRepository.save(product)
    }

    fun update(product: Product): Product {
        try {
            productRepository.findById(product.id)
                ?: throw Exception("Product ID does not exist")
            if (!isValidStock(product.stock)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Stock")
            }
            return productRepository.save(product)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun updateName(product: Product): Product? {
        try {
            val response = productRepository.findById(product.id)
                ?: throw Exception("Product ID does not exist")
            response.apply {
                description = product.description
            }
            if (!isValidStock(response.stock)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Stock")
            }
            return productRepository.save(response)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun isValidStock(stock: Long?): Boolean {
        return stock != null && stock > 0
    }
}
