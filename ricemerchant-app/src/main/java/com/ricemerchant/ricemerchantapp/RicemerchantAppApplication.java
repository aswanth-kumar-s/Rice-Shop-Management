package com.ricemerchant.ricemerchantapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication(exclude={SqlInitializationAutoConfiguration.class})
public class RicemerchantAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RicemerchantAppApplication.class, args);
	}
	
	@Configuration
	public class WebConfiguration extends WebMvcConfigurationSupport {

	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry){ 
	            registry.addResourceHandler("/**")
	                 .addResourceLocations("classpath:/static/");
	    }
	}
}
