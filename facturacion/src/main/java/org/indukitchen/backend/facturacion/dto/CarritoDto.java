package org.indukitchen.backend.facturacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarritoDto {

    private String id;
    private ClienteDto cliente;
    private List<DetalleDto> detalles;
}
