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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.type.TypeReference;

import cl.aacp9.model.Cliente;
import cl.aacp9.repository.IClienteRepository;
import cl.aacp9.util.TestUtil;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

	//interface que se le hará el test
	@InjectMocks 
	private IClienteService clienteServiceTest = new ClienteServiceImpl();
	
	//simula objeto 
	@Mock 
	private IClienteRepository clienteRepositoryTest;
	
	//happyPathTest
	@Test
	void ShouldReturnTrue() {
		//
		when (clienteRepositoryTest.existsById(anyInt())).thenReturn(true);
		assertTrue(clienteServiceTest.existeCliente(anyInt()));

		//se verifica la ejecucion del metodo del repositorio(mock)
		verify(clienteRepositoryTest, times(1)).existsById(anyInt());
	}

	//FalsePathTest
	@Test
	void shouldReturnFalse() {
		when (clienteRepositoryTest.existsById(anyInt())).thenReturn(false);
		assertFalse(clienteServiceTest.existeCliente(anyInt()));
		
		//se verifica la ejecucion del metodo del repositorio(mock)
		//el verify se utiliza cuando el método no retorna nada, o sea no tenemos como comparar un resultado
		//para comprobar si ejecuto correctamente, en tal caso usamo verify para al menos comprobar que el metodo 
		//es realizado sin errores
		verify(clienteRepositoryTest, times(1)).existsById(anyInt());
	}
	
	@Test
	  void shouldReturnList() {
		List<Cliente> clientes = 
				TestUtil.loadObjectFromResource(
						"clients/response/clients_list.json", new TypeReference<List<Cliente>>() {});
		when(clienteRepositoryTest.findAll())
		.thenReturn(clientes);
		
		//testing method
		List<Cliente> resultado=clienteServiceTest.findAll();
		assertThat(resultado).isEqualTo(clientes);
		verify(clienteRepositoryTest, times(1)).findAll();
	}

	@Test
	void shouldReturnClientDisable() throws IOException {
		Cliente cliente =
				TestUtil.loadObjectFromResource(
						"clients/response/client_entity.json", Cliente.class);
		
		Cliente clienteUpdated=
				TestUtil.loadObjectFromResource(
						"clients/response/client_projection.json", Cliente.class);
		
		when(clienteRepositoryTest.findById(anyInt())).thenReturn(java.util.Optional.of(cliente));
		when(clienteRepositoryTest.save(any(Cliente.class))).thenReturn(clienteUpdated);
		
		//testing method
		Cliente resultado=clienteServiceTest.disableClient(anyInt(), clienteUpdated);
		
		verify(clienteRepositoryTest, times(1)).save(any(Cliente.class));
		assert resultado.equals(clienteUpdated);
	}
	
	//FalsePathTes
	@Test
	void shouldReturnRunTimeException() throws IOException {
		Cliente cliente = new Cliente();
		when(clienteRepositoryTest.findById(anyInt())).thenReturn(Optional.empty());
		
		//testing method
	      assertThrows(RuntimeException.class, () -> {
	            clienteServiceTest.disableClient(anyInt(), cliente);
	        }, "La excepción 'Cliente no encontrado con el ID: ' debe ser lanzada.");	
    }
}
