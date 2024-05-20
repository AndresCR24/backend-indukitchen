package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ClienteRepository extends ListCrudRepository<ClienteEntity, String> {
}
