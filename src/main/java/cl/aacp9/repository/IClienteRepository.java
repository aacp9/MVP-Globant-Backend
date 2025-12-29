package cl.aacp9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.aacp9.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
	//Ejemplos de metodos incluidos en JPA
	//save
	//findAll
	//findById
	//findAllById
	//delete
	
	@Query("SELECT CASE WHEN (cl.estado)=true THEN true ELSE false END FROM"
	          + " Cliente cl WHERE cl.id = :idReceived")
	Boolean enabledClienteById(@Param("idReceived") Integer id);
	
	
}
