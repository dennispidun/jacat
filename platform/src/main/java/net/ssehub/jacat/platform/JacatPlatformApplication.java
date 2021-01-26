package net.ssehub.jacat.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JacatPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(JacatPlatformApplication.class, args);
	}

}
