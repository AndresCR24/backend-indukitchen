package org.indukitchen.backend.facturacion.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CarritoDTO {
    private Integer id;
    private List<DetalleDTO> detalles;

    // Getters y Setters
}
