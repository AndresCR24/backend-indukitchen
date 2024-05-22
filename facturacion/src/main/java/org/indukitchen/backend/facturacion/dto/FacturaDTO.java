package org.indukitchen.backend.facturacion.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FacturaDTO {
    private Integer id;
    private Date fecha;
    private Double total;
    private ClienteDTO cliente;
    private List<CarritoDTO> carritos;

}