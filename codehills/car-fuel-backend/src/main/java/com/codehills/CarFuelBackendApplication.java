package com.codehills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan  //enables Spring Boot to detect @WebServlet classes
public class CarFuelBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarFuelBackendApplication.class, args);
    }
}
