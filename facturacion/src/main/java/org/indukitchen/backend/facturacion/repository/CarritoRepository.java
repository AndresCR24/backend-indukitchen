package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface CarritoRepository extends ListCrudRepository<CarritoEntity, Integer> {
}
