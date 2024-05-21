package org.indukitchen.backend.facturacion.service;

import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    public List<CarritoEntity> findAll() {
        return carritoRepository.findAll();
    }

    public Optional<CarritoEntity> findById(Integer id) {
        return carritoRepository.findById(id);
    }

    public CarritoEntity save(CarritoEntity carrito) {
        return carritoRepository.save(carrito);
    }

    public void deleteById(Integer id) {
        carritoRepository.deleteById(id);
    }
}