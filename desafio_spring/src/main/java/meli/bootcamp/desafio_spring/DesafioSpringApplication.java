package meli.bootcamp.desafio_spring;

import meli.bootcamp.desafio_spring.services.DBService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class DesafioSpringApplication implements CommandLineRunner {

	private final DBService dbService;

	public DesafioSpringApplication(DBService dbService) {
		this.dbService = dbService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DesafioSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Locale.setDefault(Locale.US);
		dbService.instantiateDB();
	}
}
