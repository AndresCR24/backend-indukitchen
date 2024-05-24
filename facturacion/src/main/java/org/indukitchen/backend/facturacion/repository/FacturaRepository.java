package org.indukitchen.backend.facturacion.repository;

import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;


public interface FacturaRepository extends JpaRepository<FacturaEntity, Integer> {

    @Query("SELECT f FROM FacturaEntity f JOIN FETCH f.carritoFactura c JOIN FETCH c.clienteCarrito WHERE f.id = :id")
    Optional<FacturaEntity> findByIdWithCliente(@Param("id") Integer id);
}
