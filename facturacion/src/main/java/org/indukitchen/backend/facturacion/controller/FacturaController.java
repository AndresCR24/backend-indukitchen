package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.indukitchen.backend.facturacion.service.CarritoService;
import org.indukitchen.backend.facturacion.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    @Autowired
    public FacturaController(FacturaService facturaService, CarritoService carritoService) {
        this.facturaService = facturaService;
    }

    //Operaciones básicas CRUD
    @PostMapping
    public ResponseEntity<FacturaEntity> add(@RequestBody FacturaEntity factura) {
        FacturaEntity facturaGuardada = this.facturaService.save(factura);
        return ResponseEntity.ok(facturaGuardada);
    }

    @GetMapping
    public ResponseEntity<List<FacturaEntity>> getAll() {
        List<FacturaEntity> facturas = this.facturaService.getAll();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> get(@PathVariable Integer id) {
        return ResponseEntity.ok(this.facturaService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaEntity> update(@PathVariable Integer id, @RequestBody FacturaEntity factura) {
        if (factura.getId() != null && factura.getId().equals(id) && this.facturaService.exists(id)) {
            FacturaEntity facturaActualizada = this.facturaService.save(factura);
            return ResponseEntity.ok(facturaActualizada);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (this.facturaService.exists(id)) {
            this.facturaService.deleteFactura(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}

