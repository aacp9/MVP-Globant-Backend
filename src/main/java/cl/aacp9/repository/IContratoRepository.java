package cl.aacp9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.aacp9.model.Contrato;

public interface IContratoRepository extends JpaRepository<Contrato, Integer> {
	//Ejemplos de metodos incluidos en JPA
	//save
	//findAll
	//findById
	//findAllById
	//delete
	
	//List<Contrato> findByIdCliente(Integer id);
}
