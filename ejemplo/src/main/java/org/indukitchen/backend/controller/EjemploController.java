package org.indukitchen.backend.controller;


import org.indukitchen.backend.model.Libro;
import org.indukitchen.backend.service.LibroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EjemploController {

    private final LibroService libroService;

    public EjemploController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/libros")
    public List<Libro> consultarLibros(){
        return libroService.consultarLibros();
    }
}
