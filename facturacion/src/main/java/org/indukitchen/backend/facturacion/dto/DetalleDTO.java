package org.indukitchen.backend.facturacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DetalleDTO {
    private Integer productoId;
    private String productoNombre;
    private Integer cantidad;

    // Getters y Setters
}
