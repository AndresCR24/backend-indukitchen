package org.indukitchen.backend.facturacion.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithAttachment(String to, String subject, String text, byte[] pdfBytes, String pdfFilename) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Asegúrate de que el remitente es la dirección de correo autenticada
        helper.setFrom("trabajo.indukitchen@hotmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.addAttachment(pdfFilename, new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }
}