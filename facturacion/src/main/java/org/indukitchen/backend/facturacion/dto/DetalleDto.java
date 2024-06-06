package org.indukitchen.backend.facturacion.dto;

import lombok.Data;

@Data
public class DetalleDto {
    private String idCarrito;
    private String idProducto;
    private Integer cantidad;
}
