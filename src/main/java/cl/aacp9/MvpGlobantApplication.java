package cl.aacp9;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.aacp9.model.Role;
import cl.aacp9.model.User;
import cl.aacp9.repository.RoleRepository;
import cl.aacp9.repository.UserRepository;

@SpringBootApplication
public class MvpGlobantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvpGlobantApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		return args -> {

			Role adminRole = new Role("ADMIN");
			Role userRole = new Role("USER");

			roleRepository.saveAll(List.of(adminRole, userRole));

			User admin = new User();
			admin.setUsername("admin");
			admin.addRole(adminRole);
			admin.setPassword(passwordEncoder.encode("admin"));

			User user = new User();
			user.setUsername("user");
			user.addRole(userRole);
			user.setPassword(passwordEncoder.encode("user"));

			userRepository.saveAll(List.of(admin, user));
		};
	}
	
}
