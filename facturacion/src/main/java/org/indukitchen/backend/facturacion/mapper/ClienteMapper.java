package org.indukitchen.backend.facturacion.mapper;

import org.indukitchen.backend.facturacion.dto.ClienteDto;
import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDto aDto(ClienteEntity clienteEntity);

    ClienteEntity aEntidad(ClienteDto clienteDto);
}
