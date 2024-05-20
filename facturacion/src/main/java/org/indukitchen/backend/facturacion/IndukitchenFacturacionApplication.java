package org.indukitchen.backend.facturacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories

public class IndukitchenFacturacionApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndukitchenFacturacionApplication.class, args);
    }
}