package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication  //dont forget this is set at the class level; meaning at the very top 
public class PetStoreApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(PetStoreApplication.class, args);

	}

}
