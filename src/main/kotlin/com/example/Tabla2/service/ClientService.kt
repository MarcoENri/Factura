package com.example.Tabla2.service

import com.example.Tabla2.entity.Client
import com.example.Tabla2.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ClientService {

    @Autowired
    lateinit var clientRepository: ClientRepository

    fun list(): List<Client> {
        return clientRepository.findAll()
    }

    fun save(client: Client): Client {
        if (!isValidCedula(client.nui)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid NUI")
        }
        return clientRepository.save(client)
    }

    fun update(client: Client): Client {
        try {
            clientRepository.findById(client.id)
                ?: throw Exception("Client ID does not exist")
            if (!isValidCedula(client.nui)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid NUI")
            }
            return clientRepository.save(client)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun updateName(client: Client): Client? {
        try {
            val response = clientRepository.findById(client.id)
                ?: throw Exception("Client ID does not exist")
            response.apply {
                nui = client.nui
            }
            if (!isValidCedula(response.nui)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid NUI")
            }
            return clientRepository.save(response)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun validarNui(nui: String?): Boolean {
        return isValidCedula(nui)
    }

    private fun isValidCedula(cedula: String?): Boolean {
        if (cedula.isNullOrBlank() || cedula.length != 10) {
            return false
        }
        return try {
            val province = cedula.substring(0, 2).toInt()
            if (province !in 1..24) {
                return false
            }
            val coefficients = intArrayOf(2, 1, 2, 1, 2, 1, 2, 1, 2)
            var sum = 0
            for (i in coefficients.indices) {
                val digit = cedula.substring(i, i + 1).toInt() * coefficients[i]
                sum += digit % 10 + digit / 10
            }
            val lastDigit = cedula.substring(9, 10).toInt()
            (sum % 10 == 0).let { checkDigit -> if (!checkDigit) 10 - (sum % 10) else 0 } == lastDigit
        } catch (e: NumberFormatException) {
            false
        }
    }
}
