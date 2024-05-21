package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.ProductoEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductoRepository extends ListCrudRepository<ProductoEntity, Integer> {
}
