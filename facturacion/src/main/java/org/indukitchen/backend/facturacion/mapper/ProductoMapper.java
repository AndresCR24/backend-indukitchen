package org.indukitchen.backend.facturacion.mapper;

import org.indukitchen.backend.facturacion.dto.ProductoDto;
import org.indukitchen.backend.facturacion.model.ProductoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoDto aDto(ProductoEntity productoEntity);
    ProductoEntity aEntidad(ProductoDto productoDto);
    List<ProductoDto> aDtos(List<ProductoEntity> productoEntities);
    List<ProductoEntity> aEntidades(List<ProductoDto> productoDtos);
}
