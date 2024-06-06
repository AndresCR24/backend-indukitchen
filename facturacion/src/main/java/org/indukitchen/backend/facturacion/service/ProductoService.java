package org.indukitchen.backend.facturacion.service;

import org.indukitchen.backend.facturacion.mapper.ProductoMapper;
import org.indukitchen.backend.facturacion.dto.ProductoDto;
import org.indukitchen.backend.facturacion.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;


    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    public List<ProductoDto> getAll() {
        return productoMapper.aDtos(this.productoRepository.findAll());
    }

    public ProductoDto get(String idProducto) {
        return this.productoMapper.aDto(
                this.productoRepository.findById(UUID.fromString(idProducto)).orElse(null));
    }

    public ProductoDto save(ProductoDto producto) {
        return this.productoMapper.aDto(
                this.productoRepository.save(
                        this.productoMapper.aEntidad(producto)));
    }

    public boolean exists(String idProducto) {
        return this.productoRepository.existsById(UUID.fromString(idProducto));
    }

    public void deleteProducto(String idProducto) {
        this.productoRepository.deleteById(UUID.fromString(idProducto));
    }

}
