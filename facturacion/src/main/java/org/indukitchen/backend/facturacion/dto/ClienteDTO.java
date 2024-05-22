package org.indukitchen.backend.facturacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {
    private String cedula;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correoElectronico;

    // Getters y Setters
}
