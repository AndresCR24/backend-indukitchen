package org.indukitchen.backend.facturacion.controller;

import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.indukitchen.backend.facturacion.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteEntity> add(@RequestBody ClienteEntity cliente)
    {
        return ResponseEntity.ok(this.clienteService.save(cliente));
    }
}
