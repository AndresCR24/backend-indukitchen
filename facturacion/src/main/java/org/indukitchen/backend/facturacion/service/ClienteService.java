package org.indukitchen.backend.facturacion.service;

import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.indukitchen.backend.facturacion.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteEntity> getAll()
    {
        return this.clienteRepository.findAll();
    }

    public ClienteEntity save(ClienteEntity usuario)
    {
        return this.clienteRepository.save(usuario);
    }
}
