package com.pokeapigo.core.common.config;

import com.pokeapigo.core.common.config.properties.ApplicationPropertiesConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private ApplicationPropertiesConfig applicationPropertiesConfig;

    public OpenApiConfig(ApplicationPropertiesConfig applicationPropertiesConfig) {
        this.applicationPropertiesConfig = applicationPropertiesConfig;
    }

    @Bean
    public OpenAPI openApiInformation(BuildProperties buildProperties) {
        Server localServer = new Server()
                .url("http://localhost:" + applicationPropertiesConfig.getServer().getPort())
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
