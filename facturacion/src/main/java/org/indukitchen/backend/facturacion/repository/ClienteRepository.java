package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {
}
