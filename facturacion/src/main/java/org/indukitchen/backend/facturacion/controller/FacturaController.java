package org.indukitchen.backend.facturacion.controller;

import jakarta.mail.MessagingException;
import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.indukitchen.backend.facturacion.service.CarritoService;
import org.indukitchen.backend.facturacion.service.EmailService;
import org.indukitchen.backend.facturacion.service.FacturaService;
import org.indukitchen.backend.facturacion.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador para gestionar las facturas.
 */
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    /**
     * Inyeccion de dependencias
     */
    private final FacturaService facturaService;
    private final PdfService pdfService;
    private final EmailService emailService;

    /**
     * Constructor del FacturaController con inyección de dependencias.
     *
     * @param facturaService Servicio para operaciones de facturas.
     * @param pdfService Servicio para generación de PDFs.
     * @param emailService Servicio para envío de correos electrónicos.
     */
    @Autowired
    public FacturaController(FacturaService facturaService, PdfService pdfService, EmailService emailService) {
        this.facturaService = facturaService;
        this.pdfService = pdfService;
        this.emailService = emailService;
    }

    /**
     * Añade una nueva factura.
     *
     * @param factura a añadir.
     * @return La factura guardada.
     */
    //Operaciones básicas CRUD
    @PostMapping
    public ResponseEntity<FacturaEntity> add(@RequestBody FacturaEntity factura) {
        FacturaEntity facturaGuardada = this.facturaService.save(factura);
        return ResponseEntity.ok(facturaGuardada);
    }

    /**
     * Obtiene todas las facturas.
     *
     * @return Lista de todas las facturas.
     */
    @GetMapping
    public ResponseEntity<List<FacturaEntity>> getAll() {
        List<FacturaEntity> facturas = this.facturaService.getAll();
        return ResponseEntity.ok(facturas);
    }
    /*
    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> get(@PathVariable Integer id) {
        return ResponseEntity.ok(this.facturaService.get(id));
    }
*/
    /**
     * Actualiza una factura existente.
     *
     * @param id de la factura a actualizar.
     * @param factura actualizada.
     * @return La factura actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FacturaEntity> update(@PathVariable Integer id, @RequestBody FacturaEntity factura) {
        if (factura.getId() != null && factura.getId().equals(id) && this.facturaService.exists(id)) {
            FacturaEntity facturaActualizada = this.facturaService.save(factura);
            return ResponseEntity.ok(facturaActualizada);
        }

        return ResponseEntity.badRequest().build();
    }

    /**
     * Elimina una factura por su ID.
     *
     * @param id de la factura a eliminar.
     * @return Respuesta vacía si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (this.facturaService.exists(id)) {
            this.facturaService.deleteFactura(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> get(@PathVariable Integer id) {
        FacturaEntity factura = facturaService.get(id);
        if (factura != null) {
            return ResponseEntity.ok(factura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

 */
    /**
     * Obtiene una factura por su ID.
     *
     * @param id de la factura.
     * @return La factura correspondiente al ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> get(@PathVariable Integer id) {
        FacturaEntity factura = facturaService.get(id);
        if (factura != null) {
            return ResponseEntity.ok(factura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Genera y devuelve el PDF de una factura.
     *
     * @param id de la factura.
     * @return El PDF de la factura.
     */

    @GetMapping("/{id}/pdf")
    public ResponseEntity<InputStreamResource> getPdf(@PathVariable Integer id) {
        FacturaEntity factura = facturaService.get(id);
        if (factura != null) {
            ByteArrayOutputStream baos = pdfService.generateFacturaPdf(factura);
            ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=factura_" + id + ".pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(baos.size())
                    .body(new InputStreamResource(bis));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Calcula el total de una factura por su ID.
     *
     * @param id de la factura.
     * @return El total de la factura.
     */
    @GetMapping("/{id}/total")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable Integer id) {
        BigDecimal total = facturaService.calculateTotal(id);
        return ResponseEntity.ok(total);
    }

    /**
     * Genera y envía el PDF de una factura por correo electrónico.
     *
     * @param id de la factura.
     * @return Respuesta indicando si el envío fue exitoso o no.
     */
    @PostMapping("/{id}/enviar-pdf")
    public ResponseEntity<String> generarYEnviarFactura(@PathVariable Integer id) {
        // Obtener la factura
        FacturaEntity factura = facturaService.get(id);

        if (factura == null) {
            return ResponseEntity.notFound().build();
        }

        // Generar PDF de la factura
        ByteArrayOutputStream pdfOutputStream = pdfService.generateFacturaPdf(factura);
        byte[] pdfBytes = pdfOutputStream.toByteArray();

        // Obtener la dirección de correo del cliente
        String emailCliente = factura.getCarritoFactura().getClienteCarrito().getCorreo();

        // Enviar el correo electrónico con la factura adjunta
        try {
            emailService.sendEmailWithAttachment(
                    emailCliente,
                    "Tu factura de Indukitchen",
                    "Adjunto encontrarás tu factura.",
                    pdfBytes,
                    "factura.pdf"
            );
            return ResponseEntity.ok("Factura generada y enviada exitosamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al enviar la factura por correo electrónico.");
        }
    }

}

