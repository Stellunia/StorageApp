package stellunia.StorageApp;

import jakarta.persistence.Entity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import stellunia.StorageApp.file.StorageProperties;
import stellunia.StorageApp.file.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageAppApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService fileService) {
		return (args) -> {
			fileService.init();
		};
	}

}
