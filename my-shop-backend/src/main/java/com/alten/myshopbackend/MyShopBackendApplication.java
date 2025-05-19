package com.alten.myshopbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.alten.myshopbackend.domain")
public class MyShopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyShopBackendApplication.class, args);
    }

}
