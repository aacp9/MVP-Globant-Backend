package cl.aacp9.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.type.TypeReference;

import cl.aacp9.model.Contrato;
import cl.aacp9.repository.IContratoRepository;
import cl.aacp9.util.TestUtil;

@ExtendWith(MockitoExtension.class)
public class ContratoServiceTest {

	//interface que se le hará el test
	@InjectMocks 
	private IContratoService contratoServiceTest = new ContratoServiceImpl();
	
	//simula objeto 
	@Mock 
	private IContratoRepository contratoRepositoryTest;



	@Test
	  void shouldReturnList() {
		//list of all contracts
		List<Contrato> contratos = 
				TestUtil.loadObjectFromResource(
						"contracts/response/contracts_list.json", new TypeReference<List<Contrato>>() {});
		when(contratoRepositoryTest.findAll())
		.thenReturn(contratos);
		
		//testing method
		List<Contrato> resultado=contratoServiceTest.findAll();
		assertThat(resultado).isEqualTo(contratos);
		verify(contratoRepositoryTest, times(1)).findAll();
	}

	//sadPathTest
	@Test
	void ShouldReturnThrowInFindAll() {
		when (contratoRepositoryTest.findAll()).thenThrow(new RuntimeException("test"));
		//testing method
		assertThrows(Exception.class, ()->contratoServiceTest.findAll());
		verify(contratoRepositoryTest, times(1)).findAll();
	}
	
	@Test
	void shouldRegisterContrato() throws IOException{
		Contrato contrato = 
				TestUtil.loadObjectFromResource(
						"contracts/response/contracts_entity.json", Contrato.class);
		when(contratoRepositoryTest.save(any(Contrato.class))).thenReturn(contrato);
		
		//testing method
		contratoServiceTest.save(contrato);
		verify(contratoRepositoryTest, times(1)).save(any(Contrato.class));
	}
	
	@Test
	void ShouldReturnThrowInSave()  throws IOException{
		Contrato contrato = 
				TestUtil.loadObjectFromResource(
						"contracts/request/contracts_register.json", Contrato.class);
		
		when (contratoRepositoryTest.save(any(Contrato.class))).thenThrow(new IllegalArgumentException("test"));
		//testing method
		
		assertThrows(Exception.class, ()->contratoServiceTest.save(contrato));
		verify(contratoRepositoryTest, times(1)).save(any(Contrato.class));
	}
	
	@Test
	void shouldReturnListContractByIdClient () {
		//list of all contracts
		List<Contrato> contratosByIdCliente = 
				TestUtil.loadObjectFromResource(
						"contracts/response/contracts_list_by_client_id.json", new TypeReference<List<Contrato>>() {});
		when(contratoRepositoryTest.findByIdCliente(anyInt()))
		.thenReturn(contratosByIdCliente);
		
		//testing method
		List<Contrato> resultado=contratoServiceTest.listaContratoByIdCliente(anyInt());
		assertThat(resultado).isEqualTo(contratosByIdCliente);
		verify(contratoRepositoryTest, times(1)).findByIdCliente(anyInt());
		
	}
	@Test
	void ShouldReturnThrowInListContractByIdClient() {
		when (contratoRepositoryTest.findByIdCliente(anyInt())).thenThrow(new RuntimeException("test"));
		//testing method
		assertThrows(Exception.class, ()->contratoServiceTest.listaContratoByIdCliente(anyInt()));
		verify(contratoRepositoryTest, times(1)).findByIdCliente(anyInt());
	}

	@Test
	void shouldReturnTrue() {
		List<Contrato> contratos = 
				TestUtil.loadObjectFromResource(
						"contracts/response/contracts_list_by_client_id.json", new TypeReference<List<Contrato>>() {});

		when(contratoRepositoryTest.findByIdCliente(anyInt())).thenReturn(contratos);
		assertTrue(contratoServiceTest.existeClienteConContrato(anyInt()));

		//se verifica la ejecucion del metodo del repositorio(mock)
		verify(contratoRepositoryTest, times(1)).findByIdCliente(anyInt());
		
	}
	
	@Test
	void shouldReturnFalse() {
//		List<Contrato> contratos = 
//				TestUtil.loadObjectFromResource(
//						"contracts/response/contracts_list_by_client_id.json", new TypeReference<List<Contrato>>() {});

		when(contratoRepositoryTest.findByIdCliente(anyInt())).thenReturn(Collections.emptyList());
		assertFalse(contratoServiceTest.existeClienteConContrato(anyInt()));

		//se verifica la ejecucion del metodo del repositorio(mock)
		verify(contratoRepositoryTest, times(1)).findByIdCliente(anyInt());
		
	}

	
	@Test
	void shouldReturnThrowsInFindByIdCliente() {
	when(contratoRepositoryTest.findByIdCliente(anyInt())).thenThrow(new RuntimeException("test"));

	assertThrows(Exception.class, ()->contratoServiceTest.existeClienteConContrato(anyInt()));
	
	verify(contratoRepositoryTest, times(1)).findByIdCliente(anyInt());
	}
	
}
