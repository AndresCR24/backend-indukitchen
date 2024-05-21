package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.indukitchen.backend.facturacion.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public List<FacturaEntity> getAllFacturas() {
        return facturaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> getFacturaById(@PathVariable Integer id) {
        Optional<FacturaEntity> factura = facturaService.findById(id);
        return factura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public FacturaEntity createFactura(@RequestBody FacturaEntity factura) {
        return facturaService.save(factura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaEntity> updateFactura(@PathVariable Integer id, @RequestBody FacturaEntity facturaDetails) {
        Optional<FacturaEntity> factura = facturaService.findById(id);
        if (factura.isPresent()) {
            facturaDetails.setId(id);
            return ResponseEntity.ok(facturaService.save(facturaDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable Integer id) {
        if (facturaService.findById(id).isPresent()) {
            facturaService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
