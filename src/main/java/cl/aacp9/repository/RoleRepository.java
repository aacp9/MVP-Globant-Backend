package cl.aacp9.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.aacp9.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(String name);
}
