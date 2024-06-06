package org.indukitchen.backend.facturacion.mapper;

import org.indukitchen.backend.facturacion.dto.CarritoDto;
import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface CarritoMapper {
    CarritoDto aDto(CarritoEntity carrito);

    CarritoEntity aEntidad(CarritoDto carritoDto);
}
