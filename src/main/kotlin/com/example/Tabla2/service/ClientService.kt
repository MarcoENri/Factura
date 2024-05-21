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
        return clientRepository.save(client)
    }

    fun update(client: Client): Client {
        try {
            clientRepository.findById(client.id)
                ?: throw Exception("Ya existe el id")
            return clientRepository.save(client)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

    fun updateName(client: Client): Client? {
        try {
            var response = clientRepository.findById(client.id)
                ?: throw Exception("Ya existe el id")
            response.apply {
               nui= client.nui
            }
            return clientRepository.save(response)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }
}
