package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarritoRepository extends JpaRepository<CarritoEntity, UUID> {
}
