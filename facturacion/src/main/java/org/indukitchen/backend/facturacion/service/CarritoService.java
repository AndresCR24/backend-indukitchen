package org.indukitchen.backend.facturacion.service;

import jakarta.mail.MessagingException;
import org.indukitchen.backend.facturacion.dto.CarritoDto;
import org.indukitchen.backend.facturacion.mapper.CarritoMapper;
import org.indukitchen.backend.facturacion.mapper.ClienteMapper;
import org.indukitchen.backend.facturacion.mapper.DetalleMapper;
import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.indukitchen.backend.facturacion.model.DetalleEntity;
import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.indukitchen.backend.facturacion.repository.CarritoRepository;
import org.indukitchen.backend.facturacion.repository.ClienteRepository;
import org.indukitchen.backend.facturacion.repository.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CarritoService {

    private final FacturaService facturaService;
    private final CarritoRepository carritoRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final CarritoMapper carritoMapper;
    private final DetalleMapper detalleMapper;
    private final DetalleRepository detalleRepository;

    @Autowired
    public CarritoService(FacturaService facturaService, CarritoRepository carritoRepository, ClienteRepository clienteRepository, ClienteMapper clienteMapper, CarritoMapper carritoMapper, DetalleMapper detalleMapper,
                          DetalleRepository detalleRepository) {
        this.facturaService = facturaService;
        this.carritoRepository = carritoRepository;
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.carritoMapper = carritoMapper;
        this.detalleMapper = detalleMapper;
        this.detalleRepository = detalleRepository;
    }

    public List<CarritoEntity> getAll() {
        return this.carritoRepository.findAll();
    }

    public CarritoEntity get(String idCarrito)
    {
        return this.carritoRepository.findById(UUID.fromString(idCarrito)).orElse(null);
    }

    public CarritoEntity save(CarritoDto carritoDto)
    {
        return this.carritoRepository.save(carritoMapper.aEntidad(carritoDto));
    }

    public boolean exists(String idCarrito)
    {
        return this.carritoRepository.existsById(UUID.fromString(idCarrito));
    }

    public void deleteUsuario(String idCarrito){
        this.carritoRepository.deleteById(UUID.fromString(idCarrito));
    }

    @Transactional(noRollbackFor = MessagingException.class)
    public CarritoDto procesarCarrito(CarritoDto carritoDto) {
        // TODO validar datos del carrito
        // Se almacena información del cliente
        ClienteEntity cliente = clienteMapper.aEntidad(carritoDto.getCliente());
        cliente = this.clienteRepository.save(cliente);

        //se crea un nuevo carrito
        CarritoEntity carrito = new CarritoEntity();
        carrito.setIdCliente(cliente.getCedula());
        carrito = this.carritoRepository.save(carrito);

        //se almacenan los detalles
        List<DetalleEntity> detalles = detalleMapper.aEntidades(carritoDto.getDetalles());
        for (DetalleEntity detalle : detalles) {
            detalle.setIdCarrito(carrito.getId());
        }
        List<DetalleEntity> detallesEntities = detalleRepository.saveAll(detalles);

        // se completa el objeto carrito con los objetos relacionados almacenados
        carrito.setCliente(cliente);
        carrito.setDetalles(detallesEntities);

        //se genera y envía la factura
        FacturaEntity factura = new FacturaEntity();
        factura.setIdCarrito(carrito.getId());
        factura.setCreatedAt(LocalDateTime.now());
        factura.setCarritoFactura(carrito);
        factura = this.facturaService.save(factura);

        generarEnviarFactura(factura);

        return carritoMapper.aDto(carrito);
    }

    private void generarEnviarFactura(FacturaEntity factura){

        // Generar PDF de la factura utilizando el metodo de Pdf de facturaService
        ByteArrayOutputStream pdfOutputStream = facturaService.generateFacturaPdf(factura);
        byte[] pdfBytes = pdfOutputStream.toByteArray();

        // Obtener la dirección de correo del cliente
        String emailCliente = factura.getCarritoFactura().getCliente().getCorreo();

        // Enviar el correo electrónico con la factura adjunta manejando la excepciones para entender bien
        // si se genera un error
        try {
            facturaService.sendEmailWithAttachment(
                    emailCliente,
                    "Tu factura de Indukitchen",
                    "Adjunto encontrarás tu factura.",
                    pdfBytes,
                    "factura.pdf"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}