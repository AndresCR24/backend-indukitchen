package org.indukitchen.backend.facturacion.mapper;

import org.indukitchen.backend.facturacion.dto.DetalleDto;
import org.indukitchen.backend.facturacion.model.DetalleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetalleMapper {

    DetalleDto aDto(DetalleEntity detalleEntity);

    DetalleEntity aEntidad(DetalleDto detalleDto);

    List<DetalleDto> aDtos(List<DetalleEntity> detallesEntities);

    List<DetalleEntity> aEntidades(List<DetalleDto> detallesDtos);
}
