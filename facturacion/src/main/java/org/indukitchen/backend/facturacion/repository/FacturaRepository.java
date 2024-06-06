package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface FacturaRepository extends JpaRepository<FacturaEntity, UUID> {

    @Query("SELECT f FROM FacturaEntity f JOIN FETCH f.carritoFactura c JOIN FETCH c.cliente WHERE f.id = :id")
    Optional<FacturaEntity> findByIdWithCliente(@Param("id") Integer id);
}
