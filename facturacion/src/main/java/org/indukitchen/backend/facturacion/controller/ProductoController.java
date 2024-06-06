package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.dto.ProductoDto;
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
    public ResponseEntity<ProductoDto> add(@RequestBody ProductoDto producto) {
        ProductoDto productoGuardado = this.productoService.save(producto);
        return ResponseEntity.ok(productoGuardado);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDto>> getAll() {
        List<ProductoDto> productos = this.productoService.getAll();
        return ResponseEntity.ok(productos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> get(@PathVariable String id) {
        ProductoDto producto = this.productoService.get(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> update(@PathVariable String id, @RequestBody ProductoDto producto) {
        if (producto.getId() != null && producto.getId().equals(id) && this.productoService.exists(id)) {
            ProductoDto productoActualizado = this.productoService.save(producto);
            return ResponseEntity.ok(productoActualizado);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (this.productoService.exists(id)) {
            this.productoService.deleteProducto(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
