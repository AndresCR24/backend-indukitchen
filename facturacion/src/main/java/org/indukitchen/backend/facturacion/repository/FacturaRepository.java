package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Integer> {
}
