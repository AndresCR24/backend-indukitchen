package org.indukitchen.backend.facturacion.service;

import org.indukitchen.backend.facturacion.model.ProductoEntity;
import org.indukitchen.backend.facturacion.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoEntity> getAll()
    {
        return this.productoRepository.findAll();
    }

    public ProductoEntity get(Integer idProducto)
    {
        return this.productoRepository.findById(idProducto).orElse(null);
    }

    public ProductoEntity save(ProductoEntity producto)
    {
        return this.productoRepository.save(producto);
    }

    public boolean exists(int idProducto)
    {
        return this.productoRepository.existsById(idProducto);
    }

    public void deleteProducto(int idProducto){
        this.productoRepository.deleteById(idProducto);
    }

}
