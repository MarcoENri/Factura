package com.example.Tabla2.controller

import com.example.Tabla2.entity.Detail
import com.example.Tabla2.service.DetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/detail")
class DetailController {
    @Autowired
    lateinit var detailService: DetailService

    @GetMapping
    fun list(): List<Detail> {
        return detailService.list()
    }

    @PostMapping
    fun save(@RequestBody detail: Detail): ResponseEntity<Detail> {
        // Validar que la entidad Detail no sea nula y contenga datos válidos
        if (detail.id != null) {
            return ResponseEntity.badRequest().build()
        }

        val savedDetail = detailService.save(detail)
        return ResponseEntity(savedDetail, HttpStatus.CREATED)
    }

    @PutMapping
    fun update(@RequestBody detail: Detail): ResponseEntity<Detail>{
        return ResponseEntity(detailService.update(detail), HttpStatus.OK)
    }

    @PatchMapping
    fun patch(@RequestBody detail: Detail): ResponseEntity<Detail>{
        return ResponseEntity(detailService.updateName(detail), HttpStatus.OK)
    }

}



