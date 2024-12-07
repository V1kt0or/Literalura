package com.example.litealura;

import com.example.litealura.principal.principal;
import com.example.litealura.repository.AutorRepository;
import com.example.litealura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class LitealuraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;
	public static void main(String[] args) {
		SpringApplication.run(LitealuraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal Mprincipal = new principal(libroRepository, autorRepository);
		Mprincipal.muestraMenu();
	}
}
