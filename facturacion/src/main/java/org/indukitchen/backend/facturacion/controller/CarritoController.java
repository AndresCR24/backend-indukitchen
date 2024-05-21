package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public List<CarritoEntity> getAllCarritos() {
        return carritoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoEntity> getCarritoById(@PathVariable Integer id) {
        Optional<CarritoEntity> carrito = carritoService.findById(id);
        return carrito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CarritoEntity createCarrito(@RequestBody CarritoEntity carrito) {
        return carritoService.save(carrito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarritoEntity> updateCarrito(@PathVariable Integer id, @RequestBody CarritoEntity carritoDetails) {
        Optional<CarritoEntity> carrito = carritoService.findById(id);
        if (carrito.isPresent()) {
            carritoDetails.setId(id);
            return ResponseEntity.ok(carritoService.save(carritoDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Integer id) {
        if (carritoService.findById(id).isPresent()) {
            carritoService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
