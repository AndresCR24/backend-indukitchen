package org.indukitchen.backend.service;

import org.indukitchen.backend.model.Libro;
import org.indukitchen.backend.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    private final LibroRepository libroRepository;


    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> consultarLibros(){
        return libroRepository.findAll();
    }


}
