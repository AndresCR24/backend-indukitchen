package org.indukitchen.backend.facturacion.service;

import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.indukitchen.backend.facturacion.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public List<FacturaEntity> getAll()
    {
        return this.facturaRepository.findAll();
    }
    public FacturaEntity get(Integer idFactura)
    {
        return this.facturaRepository.findById(idFactura).orElse(null);
    }

    public FacturaEntity save(FacturaEntity factura)
    {
        return this.facturaRepository.save(factura);
    }

    public boolean exists(Integer idFactura)
    {
        return this.facturaRepository.existsById(idFactura);
    }

    public void deleteFactura(Integer idUsuario){
        this.facturaRepository.deleteById(idUsuario);
    }

    public BigDecimal calculateTotal(Integer facturaId) {
        FacturaEntity factura = get(facturaId);
        if (factura == null || factura.getCarritoFactura() == null) {
            return BigDecimal.ZERO;
        }

        return factura.getCarritoFactura().getDetalles().stream()
                .map(detalle -> detalle.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
