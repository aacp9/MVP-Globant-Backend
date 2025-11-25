package cl.aacp9.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.aacp9.model.Cliente;
import cl.aacp9.repository.IClienteRepository;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

	//interface que se le hará el test
	@InjectMocks private IClienteService clienteServiceTest = new ClienteServiceImpl();
	
	//simula objeto 
	@Mock private IClienteRepository clienteRepositoryTest;
	
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
	
	

}
