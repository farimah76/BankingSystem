package azki.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {
	private static final Logger logger = LoggerFactory.getLogger(ProjectApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
		logger.info("ProjectApplication of azki company is Run");
	}

}
