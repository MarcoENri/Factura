package com.example.Tabla2.service

import com.example.Tabla2.entity.Client
import com.example.Tabla2.repository.ClientRepository
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class ClientServiceTest {

    @InjectMocks
    lateinit var clientService: ClientService

    @Mock
    lateinit var clientRepository: ClientRepository

    val jsonString = File("./src/test/resources/client/newClient.json").readText(Charsets.UTF_8)
    val clientMock = Gson().fromJson(jsonString, Client::class.java)

    @Test
    fun saveClientCorrect() {
        Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
        val response = clientService.save(clientMock)
        Assertions.assertEquals(response.id, clientMock.id)
    }

    @Test
    fun saveWhenNuiClientIsCorrect() {
        clientMock.nui = "0912345678" // Ejemplo de una cédula válida
        Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
        val response = clientService.validarNui(clientMock.nui)
        Assertions.assertTrue(response)
    }

    @Test
    fun saveWhenNuiClientIsIncorrect() {
        clientMock.nui = "0912345679" // Ejemplo de una cédula inválida
        Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
        val response = clientService.validarNui(clientMock.nui)
        Assertions.assertFalse(response)
    }

    @Test
    fun saveWhenNuiClientIsBlanco() {
        clientMock.nui = ""
        Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
        val response = clientService.validarNui(clientMock.nui)
        Assertions.assertFalse(response)
    }

    @Test
    fun saveWhenNuiClientInComplete() {
        clientMock.nui = "15427" // Ejemplo de una cédula incompleta
        Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
        val response = clientService.validarNui(clientMock.nui)
        Assertions.assertFalse(response)
    }
}
