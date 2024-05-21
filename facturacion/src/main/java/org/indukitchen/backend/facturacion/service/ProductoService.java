package org.indukitchen.backend.facturacion.service;

import org.indukitchen.backend.facturacion.model.ProductoEntity;
import org.indukitchen.backend.facturacion.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoEntity> findAll() {
        return productoRepository.findAll();
    }

    public Optional<ProductoEntity> findById(Integer id) {
        return productoRepository.findById(id);
    }

    public ProductoEntity save(ProductoEntity producto) {
        return productoRepository.save(producto);
    }

    public void deleteById(Integer id) {
        productoRepository.deleteById(id);
    }
}
