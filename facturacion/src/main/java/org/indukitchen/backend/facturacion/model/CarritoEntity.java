package org.indukitchen.backend.facturacion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Representa la entidad del carrito de compras.
 */
@Entity
@Table(name = "carritos")
@Getter
@Setter
@NoArgsConstructor
public class CarritoEntity {
    /**
     * Identificador único del carrito.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Identificador del cliente asociado con el carrito.
     * Se refiere a la cédula del cliente.
     */
    @Column(name = "cedula_cliente", nullable = false)
    private String idCliente;

    /**
     * Fecha y hora de creación del carrito.
     * Este campo se llena automáticamente al crear el carrito.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de la última actualización del carrito.
     * Este campo se actualiza automáticamente cada vez que se modifica el carrito.
     */
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Lista de detalles asociados con el carrito.
     * Cada detalle representa un producto en el carrito.
     * Se maneja la relación bidireccional con la entidad DetalleEntity.
     */
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<DetalleEntity> detalles;

    /**
     * Cliente asociado con el carrito.
     * Se maneja la relación bidireccional con la entidad ClienteEntity.
     */
    @ManyToOne
    @JoinColumn(name = "cedula_cliente", referencedColumnName = "cedula", insertable = false, updatable = false)
    private ClienteEntity cliente;
}
