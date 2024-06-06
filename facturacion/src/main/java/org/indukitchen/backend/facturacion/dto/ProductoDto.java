package org.indukitchen.backend.facturacion.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Data
public class ProductoDto {

    private UUID id;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private Integer existencia;

    private Double peso;

    private String imagen;

}
