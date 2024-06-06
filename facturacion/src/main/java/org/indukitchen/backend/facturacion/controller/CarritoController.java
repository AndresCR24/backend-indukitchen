package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.dto.CarritoDto;
import org.indukitchen.backend.facturacion.dto.ClienteDto;
import org.indukitchen.backend.facturacion.dto.DetalleDto;
import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.indukitchen.backend.facturacion.service.CarritoService;
import org.indukitchen.backend.facturacion.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public ResponseEntity<CarritoDto> add(@RequestBody CarritoDto carritoDto)
    {
        return ResponseEntity.ok(this.carritoService.procesarCarrito(carritoDto));
    }

    @GetMapping
    public ResponseEntity<List<CarritoEntity>> getAll()
    {
        return ResponseEntity.ok(this.carritoService.getAll());
    }

    @GetMapping("/{idCarrito}")
    public ResponseEntity<CarritoEntity> get(@PathVariable String idCarrito)
    {
        return ResponseEntity.ok(this.carritoService.get(idCarrito));
    }

    @PutMapping
    public ResponseEntity<CarritoEntity> update(@RequestBody CarritoDto carrito)
    {
        if(carrito.getId() != null && this.carritoService.exists(carrito.getId()))
        {
            return ResponseEntity.ok(this.carritoService.save(carrito));
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id_carrito}")
    public ResponseEntity<Void> delete(@PathVariable String idCarrito){
        if (this.carritoService.exists(idCarrito)){
            this.carritoService.deleteUsuario(idCarrito);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //ENcontrar datos del usuario por su carrito
    @GetMapping("/{id}/usuario")
    public ResponseEntity<?> getUsuarioByCarritoId(@PathVariable String id) {
        CarritoEntity carrito = carritoService.get(id);
        if (carrito != null) {
            return ResponseEntity.ok(carrito.getCliente());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}