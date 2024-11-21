package vttp.ssf_person_12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class SsfPerson12Application {

	public static void main(String[] args) {
		SpringApplication.run(SsfPerson12Application.class, args);
	}

	@Bean //this sets up the logging functions. then instantiate the logger in the controller
	public CommonsRequestLoggingFilter log() {
		CommonsRequestLoggingFilter logger = new CommonsRequestLoggingFilter();
		logger.setIncludeClientInfo(true);
		logger.setIncludeQueryString(true);
		return logger;
	}

}
