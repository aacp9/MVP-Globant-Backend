package cl.aacp9.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

import cl.aacp9.repository.IPlanRepository;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {
	
	@InjectMocks
	private IPlanService planServiceTest = new PlanServiceImpl();
	
	@Mock
	private IPlanRepository planRepositoryTest;
	
	//happyPathTest
	@Test
	void ShouldReturnTrue() {
		when (planRepositoryTest.existsById(anyInt())).thenReturn(true);
		assertTrue(planServiceTest.existePlan(anyInt()));
		
		//se verifica la ejecucion del metodo del repositorio(mock)
		verify(planRepositoryTest, times(1)).existsById(anyInt());
	}

	//FalsePathTest
	@Test
	void ShouldReturnFalse() {
		when (planRepositoryTest.existsById(anyInt())).thenReturn(false);
		assertFalse(planServiceTest.existePlan(anyInt()));
		
		//se verifica la ejecucion del metodo del repositorio(mock)
		//el verify se utiliza cuando el método no retorna nada, o sea no tenemos como comparar un resultado
		//para comprobar si ejecuto correctamente, en tal caso usamo verify para al menos comprobar que el metodo 
		//es realizado sin errores
		verify(planRepositoryTest, times(1)).existsById(anyInt());
	}


}
