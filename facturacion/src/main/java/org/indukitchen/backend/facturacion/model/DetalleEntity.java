package org.indukitchen.backend.facturacion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalles")
@Getter
@Setter
@NoArgsConstructor
public class DetalleEntity {

    @EmbeddedId
    private DetalleId id = new DetalleId();

    @ManyToOne
    @MapsId("idCarrito")
    @JoinColumn(name = "id_carrito")
    @JsonBackReference
    private CarritoEntity carrito;

    @ManyToOne
    @MapsId("idProducto")
    private ProductoEntity producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

}
