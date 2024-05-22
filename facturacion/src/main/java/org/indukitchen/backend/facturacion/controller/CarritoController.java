package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.indukitchen.backend.facturacion.service.CarritoService;
import org.indukitchen.backend.facturacion.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    private final CarritoService carritoService;

    @Autowired
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    //Operaciones basicas CRUD


    @PostMapping
    public ResponseEntity<CarritoEntity> add(@RequestBody CarritoEntity carrito)
    {
        return ResponseEntity.ok(this.carritoService.save(carrito));
    }

    @GetMapping
    public ResponseEntity<List<CarritoEntity>> getAll()
    {
        return ResponseEntity.ok(this.carritoService.getAll());
    }

    @GetMapping("/{idCarrito}")
    public ResponseEntity<CarritoEntity> get(@PathVariable Integer idCarrito)
    {
        return ResponseEntity.ok(this.carritoService.get(idCarrito));
    }

    @PutMapping
    public ResponseEntity<CarritoEntity> update(@RequestBody CarritoEntity carrito)
    {
        if(carrito.getId() != null && this.carritoService.exists(carrito.getId()))
        {
            return ResponseEntity.ok(this.carritoService.save(carrito));
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id_carrito}")
    public ResponseEntity<Void> delete(@PathVariable int idCarrito){
        if (this.carritoService.exists(idCarrito)){
            this.carritoService.deleteUsuario(idCarrito);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //ENcontrar datos del usuario por su carrito
    @GetMapping("/{id}/usuario")
    public ResponseEntity<?> getUsuarioByCarritoId(@PathVariable Integer id) {
        CarritoEntity carrito = carritoService.get(id);
        if (carrito != null) {
            return ResponseEntity.ok(carrito.getClienteCarrito());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}