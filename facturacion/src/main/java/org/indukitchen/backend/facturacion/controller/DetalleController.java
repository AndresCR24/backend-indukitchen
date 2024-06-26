package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.indukitchen.backend.facturacion.model.DetalleEntity;
import org.indukitchen.backend.facturacion.model.DetalleId;
import org.indukitchen.backend.facturacion.service.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/detalles")
public class DetalleController {

    private final DetalleService detalleService;

    @Autowired
    public DetalleController(DetalleService detalleService) {
        this.detalleService = detalleService;
    }

    //Operaciones básicas CRUD
    @PostMapping
    public ResponseEntity<DetalleEntity> add(@RequestBody DetalleEntity detalle) {
        return ResponseEntity.ok(this.detalleService.save(detalle));
    }

    @GetMapping
    public ResponseEntity<List<DetalleEntity>> getAll() {
        return ResponseEntity.ok(this.detalleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleEntity> get(@PathVariable DetalleId id) {
        DetalleEntity detalle = this.detalleService.get(id);
        if (detalle != null) {
            return ResponseEntity.ok(detalle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
    @PutMapping
    public ResponseEntity<DetalleEntity> update(@PathVariable DetalleId id, @RequestBody DetalleEntity detalle) {
        if (id != null && this.detalleService.exists(id)) {
            detalle.setId(id); // Asegurarse de que el ID es correcto
            return ResponseEntity.ok(this.detalleService.save(detalle));
        }

        return ResponseEntity.badRequest().build();
    }

     */



    @PutMapping
    public ResponseEntity<DetalleEntity> update(@RequestParam String idCarrito, @RequestParam String idProducto, @RequestBody DetalleEntity detalle) {
        try {
            DetalleId id = new DetalleId(UUID.fromString(idCarrito), UUID.fromString(idProducto));
            if (id != null && this.detalleService.exists(id)) {
                detalle.setIdCarrito(id.getIdCarrito());
                detalle.setIdCarrito(id.getIdProducto());
                return ResponseEntity.ok(this.detalleService.save(detalle));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable DetalleId id) {
        if (this.detalleService.exists(id)) {
            this.detalleService.deleteUsuario(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
