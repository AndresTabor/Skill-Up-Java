package com.alkemy.wallet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Wallet API Messirve LTD",
                version = "1.0",
                description = "Transactions without AFIP help",
                contact = @Contact(name = "Messirve LTD",
                        url = "https://discord.gg/CTDS6GUA",
                        email = "messirveltd@gmail.com"),
                termsOfService = "https://developers.google.com/terms")
)
public class WalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletApplication.class, args);
    }

}
