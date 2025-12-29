package cl.aacp9.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Modifying
    @Transactional
	@Query(value="DELETE FROM Contrato c WHERE c.cliente.id=:idReceived")
	void deleteContratoByIdCliente(
			@Param("idReceived") Integer id );
	
	@Modifying
    @Transactional
	@Query(value="DELETE FROM Contrato c WHERE c.plan.id=:idReceived")
	void deleteContratoByIdPlan(
			@Param("idReceived") Integer id );
	
	@Query("SELECT CASE WHEN COUNT(co.cliente.id)>0 THEN true ELSE false END FROM"
	          + " Contrato co WHERE co.cliente.id = :idReceived")
	Boolean existByClienteId(@Param("idReceived") Integer id);

	@Query("SELECT CASE WHEN COUNT(co.plan.id)>0 THEN true ELSE false END FROM"
	          + " Contrato co WHERE co.plan.id = :idReceived")
	Boolean existByPlanId(@Param("idReceived") Integer id);
	

}
