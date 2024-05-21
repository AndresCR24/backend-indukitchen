package org.indukitchen.backend.facturacion.service;

import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.indukitchen.backend.facturacion.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public List<FacturaEntity> findAll() {
        return facturaRepository.findAll();
    }

    public Optional<FacturaEntity> findById(Integer id) {
        return facturaRepository.findById(id);
    }

    public FacturaEntity save(FacturaEntity factura) {
        return facturaRepository.save(factura);
    }

    public void deleteById(Integer id) {
        facturaRepository.deleteById(id);
    }
}
