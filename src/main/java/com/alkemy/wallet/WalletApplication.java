package com.alkemy.wallet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "Bearer", in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(
        info = @Info(title = "Wallet API Messirve Limited",
                version = "1.0",
                description = "Transactions without AFIP help",
                contact = @Contact(name = "Messirve LTD",
                        url = "https://discord.gg/CTDS6GUA",
                        email = "messirveltd@gmail.com"),
                termsOfService = "https://developers.google.com/terms"),
        security = @SecurityRequirement(name = "Bearer"))
public class WalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletApplication.class, args);
    }

}
