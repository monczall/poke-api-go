package com.pokeapigo.core.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${server.port}")
    int portNumber;

    @Autowired
    private BuildProperties buildProperties;

    @Bean
    public OpenAPI openApiInformation() {
        System.out.println(buildProperties);
        Server localServer = new Server()
                .url("http://localhost:" + portNumber)
                .description(buildProperties.getArtifact());

        Contact contact = new Contact()
                .email(buildProperties.get("developerEmail"))
                .name(buildProperties.get("developerName"));

        Info info = new Info()
                .contact(contact)
                .description(buildProperties.get("appDescription"))
                .title(buildProperties.getName())
                .version(buildProperties.getVersion());

        return new OpenAPI().info(info).addServersItem(localServer);
    }
}
