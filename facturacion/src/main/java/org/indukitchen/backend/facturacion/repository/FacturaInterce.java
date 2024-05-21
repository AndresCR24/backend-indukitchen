package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface FacturaInterce extends JpaRepository<FacturaEntity, Integer> {
}
