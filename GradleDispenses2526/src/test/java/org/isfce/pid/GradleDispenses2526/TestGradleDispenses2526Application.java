package org.isfce.pid.GradleDispenses2526;

import org.springframework.boot.SpringApplication;

public class TestGradleDispenses2526Application {

	public static void main(String[] args) {
		SpringApplication.from(GradleDispenses2526Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
