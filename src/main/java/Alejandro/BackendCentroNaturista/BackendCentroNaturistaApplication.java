package Alejandro.BackendCentroNaturista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories(basePackages = "Alejandro/BackendCentroNaturista/Repositories")
public class BackendCentroNaturistaApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendCentroNaturistaApplication.class, args);
		System.out.println("alejo");
	}
}
