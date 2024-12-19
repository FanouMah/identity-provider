package itu.prom16.identity_provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:config.properties")
public class IdentityProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentityProviderApplication.class, args);
	}

}
