package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductoRepository extends JpaRepository<ProductoEntity, UUID> {
}
