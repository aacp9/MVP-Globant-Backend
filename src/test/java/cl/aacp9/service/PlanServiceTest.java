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

import cl.aacp9.model.Plan;
import cl.aacp9.repository.IPlanRepository;
import cl.aacp9.util.TestUtil;

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

	//FalsePathTest
	@Test
	void shouldReturnThrowInExistePlan() {
		when (planRepositoryTest.existsById(anyInt())).thenThrow(new RuntimeException("test"));
		assertThrows(Exception.class, ()->planServiceTest.existePlan(anyInt()));
		verify(planRepositoryTest, times(1)).existsById(anyInt());		
	}
	
	
	@Test
	  void shouldReturnList() {
		
		List<Plan> planes= 
				TestUtil.loadObjectFromResource(
						"plans/response/plans_list.json", new TypeReference<List<Plan>>() {});
		
		when(planRepositoryTest.findAll())
		.thenReturn(planes);
		
		//testing method
		List<Plan> resultado=planServiceTest.findAll();
		assertThat(resultado).isEqualTo(planes);
		verify(planRepositoryTest, times(1)).findAll();
	}
	
	//sadPathTest
	@Test
	void ShouldReturnThrowInFindAll() {
		when (planRepositoryTest.findAll()).thenThrow(new RuntimeException("test"));
		//testing method
		assertThrows(Exception.class, ()->planServiceTest.findAll());
		verify(planRepositoryTest, times(1)).findAll();
	}
	
	
	@Test
	void shouldReturnClientDisable() throws IOException {
		Plan plan =
				TestUtil.loadObjectFromResource(
						"plans/response/plan_entity.json", Plan.class);
		
		Plan planUpdated=
				TestUtil.loadObjectFromResource(
						"plans/response/plan_projection.json", Plan.class);
		
		when(planRepositoryTest.findById(anyInt())).thenReturn(java.util.Optional.of(plan));
		when(planRepositoryTest.save(any(Plan.class))).thenReturn(planUpdated);
		
		//testing method
		Plan resultado=planServiceTest.disablePlan(anyInt(), planUpdated);
		
		verify(planRepositoryTest, times(1)).save(any(Plan.class));
		assert resultado.equals(planUpdated);
	}
	//FalsePathTes
	@Test
	void shouldReturnRunTimeException() throws IOException {
		Plan plan = new Plan();
		when(planRepositoryTest.findById(anyInt())).thenReturn(Optional.empty());
		
		//testing method
	      assertThrows(RuntimeException.class, () -> {
	            planServiceTest.disablePlan(anyInt(), plan);
	        }, "La excepción 'Cliente no encontrado con el ID: ' debe ser lanzada.");	
    }


}
