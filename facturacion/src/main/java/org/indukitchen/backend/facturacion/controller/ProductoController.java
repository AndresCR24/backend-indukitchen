package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.ProductoEntity;
import org.indukitchen.backend.facturacion.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoEntity> getAllProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> getProductoById(@PathVariable Integer id) {
        Optional<ProductoEntity> producto = productoService.findById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductoEntity createProducto(@RequestBody ProductoEntity producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoEntity> updateProducto(@PathVariable Integer id, @RequestBody ProductoEntity productoDetails) {
        Optional<ProductoEntity> producto = productoService.findById(id);
        if (producto.isPresent()) {
            productoDetails.setId(id);
            return ResponseEntity.ok(productoService.save(productoDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        if (productoService.findById(id).isPresent()) {
            productoService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}