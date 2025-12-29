package cl.aacp9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.aacp9.model.Plan;

public interface IPlanRepository extends JpaRepository<Plan, Integer> {
	//Ejemplos de metodos incluidos en JPA
	//save
	//findAll
	//findById
	//findAllById
	//delete
	@Query("SELECT CASE WHEN (pl.estado)=true THEN true ELSE false END FROM"
	          + " Plan pl WHERE pl.id = :idReceived")
	Boolean enabledPlanById(@Param("idReceived") Integer id);
}
