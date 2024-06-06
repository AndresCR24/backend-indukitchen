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
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.indukitchen.backend.facturacion.model.DetalleEntity;
import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.indukitchen.backend.facturacion.model.ProductoEntity;
import org.indukitchen.backend.facturacion.repository.FacturaRepository;
import org.indukitchen.backend.facturacion.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacturaService {

    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###.00");


    private final FacturaRepository facturaRepository;
    private final JavaMailSender mailSender;
    private final ProductoRepository productoRepository;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository, JavaMailSender mailSender, ProductoRepository productoRepository) {
        this.facturaRepository = facturaRepository;
        this.mailSender = mailSender;
        this.productoRepository = productoRepository;
    }

    public List<FacturaEntity> getAll() {
        return this.facturaRepository.findAll();
    }

    public FacturaEntity get(String idFactura) {
        return this.facturaRepository.findById(UUID.fromString(idFactura)).orElse(null);
    }

    public FacturaEntity save(FacturaEntity factura) {
        return this.facturaRepository.save(factura);
    }

    public boolean exists(String idFactura) {
        return this.facturaRepository.existsById(UUID.fromString(idFactura));
    }

    public void deleteFactura(String idUsuario) {
        this.facturaRepository.deleteById(UUID.fromString(idUsuario));
    }

    public BigDecimal calculateTotal(String facturaId) {
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
        // Configuracion de los detalles del correo
        helper.setFrom("trabajo.indukitchen3@hotmail.com");
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
            InputStream imagePath = this.getClass().getClassLoader().getResourceAsStream("logo.png"); // Cambia esto a la ruta de tu imagen
            ImageData imageData = ImageDataFactory.create(imagePath.readAllBytes());
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
            document.add(new Paragraph("Cliente: " + factura.getCarritoFactura().getCliente().getNombre()));
            document.add(new Paragraph("Correo: " + factura.getCarritoFactura().getCliente().getCorreo()));
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
                Optional<ProductoEntity> productoOptional = productoRepository.findById(detalle.getIdProducto());
                if(productoOptional.isPresent()) {
                    ProductoEntity producto = productoOptional.get();
                    BigDecimal precioTotalProducto = producto.getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    total = total.add(precioTotalProducto);

                    //impuesto = impuesto.add();
                    table.addCell(new Cell().add(new Paragraph(producto.getNombre())));
                    table.addCell(new Cell().add(new Paragraph(detalle.getCantidad().toString())));
                    table.addCell(new Cell().add(new Paragraph(decimalFormat.format(producto.getPrecio()))));
                    //table.addCell(new Cell().add(new Paragraph(detalle.getProducto().getPrecio().toString())));
                    table.addCell(new Cell().add(new Paragraph(decimalFormat.format(precioTotalProducto))));
                    //table.addCell(new Cell().add(new Paragraph(precioTotalProducto.toString())));
                    //table.addCell(new Cell().add(new Paragraph(impuesto.toString())));
                }
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
