package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {
}
