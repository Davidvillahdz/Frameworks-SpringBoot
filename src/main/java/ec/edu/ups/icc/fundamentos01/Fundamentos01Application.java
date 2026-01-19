package ec.edu.ups.icc.fundamentos01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Fundamentos01Application {

	public static void main(String[] args) {
		SpringApplication.run(Fundamentos01Application.class, args);
	}

}
