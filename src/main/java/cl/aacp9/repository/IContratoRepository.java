package cl.aacp9.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.aacp9.model.Contrato;

public interface IContratoRepository extends JpaRepository<Contrato, Integer> {
	//Ejemplos de metodos incluidos en JPA
	//save
	//findAll
	//findById
	//findAllById
	//delete
	@Query(value="SELECT new cl.aacp9.model.Contrato(id, estado, descuento, cliente, plan) FROM Contrato WHERE cliente.id=:idReceived")
	List<Contrato> findByIdCliente(
			@Param("idReceived") Integer id );
}
