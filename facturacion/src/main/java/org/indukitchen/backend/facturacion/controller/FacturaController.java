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

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final PdfService pdfService;
    private final EmailService emailService;

    @Autowired
    public FacturaController(FacturaService facturaService, PdfService pdfService, EmailService emailService) {
        this.facturaService = facturaService;
        this.pdfService = pdfService;
        this.emailService = emailService;
    }





    //Operaciones básicas CRUD
    @PostMapping
    public ResponseEntity<FacturaEntity> add(@RequestBody FacturaEntity factura) {
        FacturaEntity facturaGuardada = this.facturaService.save(factura);
        return ResponseEntity.ok(facturaGuardada);
    }

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
    @PutMapping("/{id}")
    public ResponseEntity<FacturaEntity> update(@PathVariable Integer id, @RequestBody FacturaEntity factura) {
        if (factura.getId() != null && factura.getId().equals(id) && this.facturaService.exists(id)) {
            FacturaEntity facturaActualizada = this.facturaService.save(factura);
            return ResponseEntity.ok(facturaActualizada);
        }

        return ResponseEntity.badRequest().build();
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> get(@PathVariable Integer id) {
        FacturaEntity factura = facturaService.get(id);
        if (factura != null) {
            return ResponseEntity.ok(factura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    @GetMapping("/{id}/total")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable Integer id) {
        BigDecimal total = facturaService.calculateTotal(id);
        return ResponseEntity.ok(total);
    }

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

