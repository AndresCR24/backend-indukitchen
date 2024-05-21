package org.indukitchen.backend.facturacion.service;

import org.indukitchen.backend.facturacion.model.DetalleEntity;
import org.indukitchen.backend.facturacion.model.DetalleId;
import org.indukitchen.backend.facturacion.repository.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleService {

    @Autowired
    private DetalleRepository detalleRepository;

    public List<DetalleEntity> findAll() {
        return detalleRepository.findAll();
    }

    public Optional<DetalleEntity> findById(DetalleId id) {
        return detalleRepository.findById(id);
    }

    public DetalleEntity save(DetalleEntity detalle) {
        return detalleRepository.save(detalle);
    }

    public void deleteById(DetalleId id) {
        detalleRepository.deleteById(id);
    }
}
