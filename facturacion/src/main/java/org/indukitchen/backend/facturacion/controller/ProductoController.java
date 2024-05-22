package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.ProductoEntity;
import org.indukitchen.backend.facturacion.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    //Operaciones básicas CRUD
    @PostMapping
    public ResponseEntity<ProductoEntity> add(@RequestBody ProductoEntity producto) {
        ProductoEntity productoGuardado = this.productoService.save(producto);
        return ResponseEntity.ok(productoGuardado);
    }

    @GetMapping
    public ResponseEntity<List<ProductoEntity>> getAll() {
        List<ProductoEntity> productos = this.productoService.getAll();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> get(@PathVariable Integer id) {
        ProductoEntity producto = this.productoService.get(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoEntity> update(@PathVariable Integer id, @RequestBody ProductoEntity producto) {
        if (producto.getId() != null && producto.getId().equals(id) && this.productoService.exists(id)) {
            ProductoEntity productoActualizado = this.productoService.save(producto);
            return ResponseEntity.ok(productoActualizado);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (this.productoService.exists(id)) {
            this.productoService.deleteProducto(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
