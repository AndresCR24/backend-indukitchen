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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.indukitchen.backend.facturacion.model.DetalleEntity;
import org.indukitchen.backend.facturacion.model.FacturaEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
@Getter
@Setter
@NoArgsConstructor
public class PdfService {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###.00");


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
