package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Integer> {
}
