package org.indukitchen.backend.facturacion.service;

import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;

    @Autowired
    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public List<CarritoEntity> getAll() {
        return this.carritoRepository.findAll();
    }

    public CarritoEntity get(Integer idCarrito)
    {
        return this.carritoRepository.findById(idCarrito).orElse(null);
    }

    public CarritoEntity save(CarritoEntity usuario)
    {
        return this.carritoRepository.save(usuario);
    }

    public boolean exists(int idCarrito)
    {
        return this.carritoRepository.existsById(idCarrito);
    }

    public void deleteUsuario(int idCarrito){
        this.carritoRepository.deleteById(idCarrito);
    }
}