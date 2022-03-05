package addressbook.lab4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab4Application {
	private static final Logger log = LoggerFactory.getLogger(Lab4Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Lab4Application.class, args);
	}



}
