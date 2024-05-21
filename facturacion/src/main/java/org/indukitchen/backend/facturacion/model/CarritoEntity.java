package org.indukitchen.backend.facturacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carrito")
@Getter
@Setter
@NoArgsConstructor
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cedula_cliente", nullable = false)
    private Integer idCliente;
    /*
    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;
*/
    @Column(name = "cantidad_producto", nullable = false)
    private Integer cantidadProducto;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relación ManyToMany
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "detalle",
            joinColumns = @JoinColumn(name = "id_carrito", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_producto", referencedColumnName = "id")
    )
    @JsonIgnore
    private List<ProductoEntity> producto;

    @ManyToOne
    @JoinColumn(name = "cedula_cliente", referencedColumnName = "cedula", insertable = false, updatable = false)
    @JsonIgnore
    private ClienteEntity clienteCarrito;
}
