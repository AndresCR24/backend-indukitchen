package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.DetalleEntity;
import org.indukitchen.backend.facturacion.model.DetalleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DetalleRepository extends JpaRepository<DetalleEntity, DetalleId> {
}
