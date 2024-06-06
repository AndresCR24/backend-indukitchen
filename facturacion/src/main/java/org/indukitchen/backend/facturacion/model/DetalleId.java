package org.indukitchen.backend.facturacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class DetalleId implements Serializable {

    @Column(name = "id_carrito")
    private UUID idCarrito;

    @Column(name = "id_producto")
    private UUID idProducto;

    public DetalleId(UUID idCarrito, UUID idProducto) {
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleId that = (DetalleId) o;
        return Objects.equals(idCarrito, that.idCarrito) &&
                Objects.equals(idProducto, that.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarrito, idProducto);
    }
}
