package com.example.Tabla2.service

import com.example.Tabla2.entity.Detail
import com.example.Tabla2.repository.DetailRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class DetailService {

    @Autowired
    lateinit var detailRepository: DetailRepository

    fun list(): List<Detail> {
        return detailRepository.findAll()
    }

    fun save(detail: Detail): Detail {
        if (!isValidQuantity(detail.quantity)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Quantity")
        }
        return detailRepository.save(detail)
    }

    fun update(detail: Detail): Detail {
        try {
            detailRepository.findById(detail.id)
                ?: throw Exception("Detail ID does not exist")
            if (!isValidQuantity(detail.quantity)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Quantity")
            }
            return detailRepository.save(detail)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun updateName(detail: Detail): Detail? {
        try {
            val response = detailRepository.findById(detail.id)
                ?: throw Exception("Detail ID does not exist")
            response.apply {
                quantity = detail.quantity
            }
            if (!isValidQuantity(response.quantity)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Quantity")
            }
            return detailRepository.save(response)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun isValidQuantity(quantity: Int): Boolean {
        return quantity > 0
    }
}
