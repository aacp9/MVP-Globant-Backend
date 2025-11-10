package cl.aacp9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.aacp9.model.Plan;

public interface IPlanRepository extends JpaRepository<Plan, Integer> {
	//Ejemplos de metodos incluidos en JPA
	//save
	//findAll
	//findById
	//findAllById
	//delete
}
