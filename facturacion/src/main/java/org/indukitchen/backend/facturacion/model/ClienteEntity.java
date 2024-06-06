package org.indukitchen.backend.facturacion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.indukitchen.backend.facturacion.ayudas.PointDeserializer;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor

public class ClienteEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String cedula;

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(nullable = false, length = 40)
    private String direccion;

    @JsonDeserialize(using = PointDeserializer.class)
    @Column
    private Point localizacion;

    @Column(name = "correo_electronico")
    private String correo;

    @Column(nullable = false, length = 17)
    private String telefono;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "cliente")
    private List<CarritoEntity> carritos;
}
