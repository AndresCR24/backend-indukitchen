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

    private final DetalleRepository detalleRepository;

    @Autowired
    public DetalleService(DetalleRepository detalleRepository) {
        this.detalleRepository = detalleRepository;
    }

    public List<DetalleEntity> getAll() {
        return this.detalleRepository.findAll();
    }

    public DetalleEntity get(DetalleId idDetalle)
    {
        return this.detalleRepository.findById(idDetalle).orElse(null);
    }

    public DetalleEntity save(DetalleEntity idDetalle)
    {
        return this.detalleRepository.save(idDetalle);
    }

    public boolean exists(DetalleId idDetalle)
    {
        return this.detalleRepository.existsById(idDetalle);
    }


    public void deleteUsuario(DetalleId idDetalle){
        this.detalleRepository.deleteById(idDetalle);
    }
}
