package org.indukitchen.backend.facturacion.dto;

import lombok.Data;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;


@Data
public class ClienteDto {

    private String cedula;
    private String nombre;
    private String direccion;
    private Point localizacion;
    private String correo;
    private String telefono;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
