package org.serratec;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoFinalApplication {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR")); 
		SpringApplication.run(ProjetoFinalApplication.class, args);
	}

}
