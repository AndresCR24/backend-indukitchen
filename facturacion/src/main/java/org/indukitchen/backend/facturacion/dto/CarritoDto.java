package org.indukitchen.backend.facturacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarritoDto {

    private String id;
    private ClienteDto clienteDto;
    private List<DetalleDto> detalles;
}
