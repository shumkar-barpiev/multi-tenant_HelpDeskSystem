package com.shumkar.helpdesksystem;

import org.springframework.boot.SpringApplication;

public class TestMultiTenantHelpDeskSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(MultiTenantHelpDeskSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
