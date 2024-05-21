package org.indukitchen.backend.facturacion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle")
@Getter
@Setter
@NoArgsConstructor
public class DetalleEntity {

    @EmbeddedId
    private DetalleId id = new DetalleId();

    @ManyToOne
    @MapsId("idCarrito")
    @JoinColumn(name = "id_carrito")
    private CarritoEntity carrito;

    @ManyToOne
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}

