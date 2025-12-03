package cl.aacp9.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.aacp9.MvpGlobantApplication;
import cl.aacp9.controller.PlanController;
import cl.aacp9.model.Plan;
import cl.aacp9.service.IPlanService;
import cl.aacp9.util.TestUtil;

@WebMvcTest(controllers = PlanController.class)
@ContextConfiguration(classes = {MvpGlobantApplication.class})
public class PlanRespositoryTest {
	 @MockitoBean 
	 private IPlanService planServiceTest;
	 
	 @Autowired 
	 private MockMvc mockMvc;
	 
	 @Test
	  void shouldReturnList() throws Exception {
		 Plan plan = new Plan();
		 plan.setId(1);
		 plan.setNombre("planName");
		 plan.setPrecio(100);
		 plan.setServicio("servicioDePlan");
		 plan.setEstado(true);
		 
		 List<Plan> planList=Arrays.asList(plan);
		 when(planServiceTest.findAll()).thenReturn(planList);
		 
		 this.mockMvc
		 .perform(get("/api/v1/planes").contentType(MediaType.APPLICATION_JSON))
	       .andDo(print())
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$.[0].id").value(1));
	 }
	 @Test
	  void shouldReturnEmpty() throws Exception {
		 
		 when(planServiceTest.findAll()).thenReturn(Collections.emptyList());
		 
		 this.mockMvc
		 .perform(get("/api/v1/planes").contentType(MediaType.APPLICATION_JSON))
	       .andDo(print())
	       .andExpect(status().isNotFound());
	 }

	 @Test
	  void shouldReturnClienteResponse() throws Exception {
		 Plan planResponse = new Plan();
		 planResponse.setId(1);
		 planResponse.setNombre("planName");
		 planResponse.setPrecio(100);
		 planResponse.setServicio("servicioDePlan");
		 planResponse.setEstado(true);

		 Plan planUpdate = new Plan();
		 planUpdate.setId(1);
		 planUpdate.setNombre("planName");
		 planUpdate.setPrecio(100);
		 planUpdate.setServicio("servicioDePlan");
		 planUpdate.setEstado(true);

		 when(planServiceTest.disablePlan(anyInt(), any(Plan.class))).
		 	thenReturn(planResponse);
	 
		    this.mockMvc
	        .perform(
	            put("/api/v1/disablePlan/{id}", 1)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(TestUtil.toJson(planUpdate)))
	        .andDo(print())
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(1));

	 }

}
