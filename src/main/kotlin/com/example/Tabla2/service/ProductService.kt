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
        return productRepository.save(product)
    }

    fun update(product: Product): Product {
        try {
            productRepository.findById(product.id)
                ?: throw Exception("Ya existe el id")
            return productRepository.save(product)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun updateName(product: Product): Product? {
        try {
            var response = productRepository.findById(product.id)
                ?: throw Exception("Ya existe el id")
            response.apply {
                description= product.description
            }
            return productRepository.save(response)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }

    }

}



