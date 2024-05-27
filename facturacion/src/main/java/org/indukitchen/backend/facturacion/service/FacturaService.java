package org.indukitchen.backend.facturacion.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.indukitchen.backend.facturacion.model.CarritoEntity;
import org.indukitchen.backend.facturacion.model.ClienteEntity;
import org.indukitchen.backend.facturacion.model.DetalleEntity;
import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.indukitchen.backend.facturacion.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###.00");


    private final FacturaRepository facturaRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository, JavaMailSender mailSender) {
        this.facturaRepository = facturaRepository;
        this.mailSender = mailSender;
    }

    public List<FacturaEntity> getAll()
    {
        return this.facturaRepository.findAll();
    }
    public FacturaEntity get(Integer idFactura)
    {
        return this.facturaRepository.findById(idFactura).orElse(null);
    }

    public FacturaEntity save(FacturaEntity factura)
    {
        return this.facturaRepository.save(factura);
    }

    public boolean exists(Integer idFactura)
    {
        return this.facturaRepository.existsById(idFactura);
    }

    public void deleteFactura(Integer idUsuario){
        this.facturaRepository.deleteById(idUsuario);
    }

    public BigDecimal calculateTotal(Integer facturaId) {
        FacturaEntity factura = get(facturaId);
        if (factura == null || factura.getCarritoFactura() == null) {
            return BigDecimal.ZERO;
        }

        return factura.getCarritoFactura().getDetalles().stream()
                .map(detalle -> detalle.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Email
    public void sendEmailWithAttachment(String to, String subject, String text, byte[] pdfBytes, String pdfFilename) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("trabajo.indukitchen2@hotmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.addAttachment(pdfFilename, new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }

    public ByteArrayOutputStream generateFacturaPdf(FacturaEntity factura) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Cargar la imagen
            String imagePath = "/Users/andresdavidcardenasramirez/Desktop/trabajo-propio/indukitchen-backend/facturacion/src/main/java/org/indukitchen/backend/facturacion/ayudas/logo.png"; // Cambia esto a la ruta de tu imagen
            ImageData imageData = ImageDataFactory.create(imagePath);
            Image image = new Image(imageData);
            image.setHorizontalAlignment(HorizontalAlignment.CENTER);

            // Añadir la imagen al documento
            document.add(image);

            // Encabezado
            document.add(new Paragraph("Factura")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold());

            document.add(new Paragraph("Factura ID: " + factura.getId()));
            document.add(new Paragraph("Fecha: " + factura.getCreatedAt().toString()));
            document.add(new Paragraph("Cliente: " + factura.getCarritoFactura().getClienteCarrito().getNombre()));
            document.add(new Paragraph("Correo: " + factura.getCarritoFactura().getClienteCarrito().getCorreo()));
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Tabla de detalles del producto
            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 1, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezados de la tabla
            table.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Cantidad").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio Unitario").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio Total").setBold()));
            //table.addHeaderCell(new Cell().add(new Paragraph("IVA").setBold()));

            BigDecimal total = BigDecimal.ZERO;



            for (DetalleEntity detalle : factura.getCarritoFactura().getDetalles()) {
                BigDecimal precioTotalProducto = detalle.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad()));
                total = total.add(precioTotalProducto);

                //impuesto = impuesto.add();
                table.addCell(new Cell().add(new Paragraph(detalle.getProducto().getNombre())));
                table.addCell(new Cell().add(new Paragraph(detalle.getCantidad().toString())));
                table.addCell(new Cell().add(new Paragraph(decimalFormat.format(detalle.getProducto().getPrecio()))));
                //table.addCell(new Cell().add(new Paragraph(detalle.getProducto().getPrecio().toString())));
                table.addCell(new Cell().add(new Paragraph(decimalFormat.format(precioTotalProducto))));
                //table.addCell(new Cell().add(new Paragraph(precioTotalProducto.toString())));
                //table.addCell(new Cell().add(new Paragraph(impuesto.toString())));
            }

            // Añadir la tabla al documento
            document.add(table);

            // Calcular impuesto y total con impuesto
            BigDecimal impuesto = total.multiply(BigDecimal.valueOf(0.19));
            BigDecimal totalConImpuesto = total.add(impuesto);

            // Total
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total del Carrito: " + decimalFormat.format(total))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(14)
                    .setBold());
            document.add(new Paragraph("IVA (19%): " + decimalFormat.format(impuesto))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(14)
                    .setBold());
            document.add(new Paragraph("Total + IVA: " + decimalFormat.format(totalConImpuesto))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(14)
                    .setBold());

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out;
    }


}
