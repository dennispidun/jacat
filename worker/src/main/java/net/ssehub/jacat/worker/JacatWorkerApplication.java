package net.ssehub.jacat.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JacatWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JacatWorkerApplication.class, args);
	}

}
