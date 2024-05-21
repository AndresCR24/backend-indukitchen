package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.indukitchen.backend.facturacion.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Operaciones basicas CRUD
    @PostMapping
    public ResponseEntity<ClienteEntity> add(@RequestBody ClienteEntity cliente)
    {
        return ResponseEntity.ok(this.clienteService.save(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> getAll()
    {
        return ResponseEntity.ok(this.clienteService.getAll());
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<ClienteEntity> get(@PathVariable int cedula)
    {
        return ResponseEntity.ok(this.clienteService.get(String.valueOf(cedula)));
    }

    @PutMapping
    public ResponseEntity<ClienteEntity> update(@RequestBody ClienteEntity cliente)
    {
        if(cliente.getCedula() != null && this.clienteService.exists(cliente.getCedula()))
        {
            return ResponseEntity.ok(this.clienteService.save(cliente));
        }

        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/{cedula}")
    public ResponseEntity<Void> delete(@PathVariable String cedula){
        if (this.clienteService.exists(cedula)){
            this.clienteService.deleteUsuario(cedula);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
