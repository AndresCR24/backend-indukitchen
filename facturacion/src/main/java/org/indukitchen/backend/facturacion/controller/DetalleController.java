package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.DetalleEntity;
import org.indukitchen.backend.facturacion.model.DetalleId;
import org.indukitchen.backend.facturacion.service.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles")
public class DetalleController {

    @Autowired
    private DetalleService detalleService;

    @GetMapping
    public List<DetalleEntity> getAllDetalles() {
        return detalleService.findAll();
    }

    @GetMapping("/{idCarrito}/{idProducto}")
    public ResponseEntity<DetalleEntity> getDetalleById(@PathVariable Integer idCarrito, @PathVariable Integer idProducto) {
        DetalleId id = new DetalleId(idCarrito, idProducto);
        Optional<DetalleEntity> detalle = detalleService.findById(id);
        return detalle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleEntity createDetalle(@RequestBody DetalleEntity detalle) {
        return detalleService.save(detalle);
    }

    @PutMapping("/{idCarrito}/{idProducto}")
    public ResponseEntity<DetalleEntity> updateDetalle(@PathVariable Integer idCarrito, @PathVariable Integer idProducto, @RequestBody DetalleEntity detalleDetails) {
        DetalleId id = new DetalleId(idCarrito, idProducto);
        Optional<DetalleEntity> detalle = detalleService.findById(id);
        if (detalle.isPresent()) {
            detalleDetails.setId(id);
            return ResponseEntity.ok(detalleService.save(detalleDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idCarrito}/{idProducto}")
    public ResponseEntity<Void> deleteDetalle(@PathVariable Integer idCarrito, @PathVariable Integer idProducto) {
        DetalleId id = new DetalleId(idCarrito, idProducto);
        if (detalleService.findById(id).isPresent()) {
            detalleService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}