package com.kppo.task_manager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kppo.task_manager.model.Role;
import com.kppo.task_manager.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class TaskManagerApplication implements ApplicationRunner {
	private final RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		createRoleIfNotExists();
	}

	public void createRoleIfNotExists() {
		if (roleRepository.count() == 0) {
			Role userRole = Role.builder().name("USER").build();

			roleRepository.save(userRole);
		}
	}
}
