package cl.aacp9.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.aacp9.MvpGlobantApplication;
import cl.aacp9.controller.ContratoController;
import cl.aacp9.model.Cliente;
import cl.aacp9.model.Contrato;
import cl.aacp9.model.Plan;
import cl.aacp9.service.IClienteService;
import cl.aacp9.service.IContratoService;
import cl.aacp9.service.IPlanService;
import cl.aacp9.util.TestUtil;

@WebMvcTest(controllers = ContratoController.class)
@ContextConfiguration(classes = {MvpGlobantApplication.class})
public class ContratoRepositoryTest {
	 @MockitoBean 
	 private IClienteService clienteServiceTest;
	 @MockitoBean 
	 private IPlanService planServiceTest;
	 @MockitoBean 
	 private IContratoService contratoServiceTest;
	 
	 @Autowired 
	 private MockMvc mockMvc;
	 
	 @Test
	  void shouldReturnList() throws Exception {
		 Cliente cliente = new Cliente();
		 cliente.setId(1);
		 cliente.setNombre("firstName");
		 cliente.setApellido("lastName");
		 cliente.setRun("1-1");
		 cliente.setDireccion("adress");
		 SimpleDateFormat formato = new SimpleDateFormat("yyyy/mm/dd");
		 Date fecha=formato.parse("1990/05/15");
		 cliente.setFechaNacimiento(fecha);
		 cliente.setEstado(true);

		 Plan plan = new Plan();
		 plan.setId(1);
		 plan.setNombre("planName");
		 plan.setPrecio(100);
		 plan.setServicio("servicioDePlan");
		 plan.setEstado(true);
		 
		 Contrato contrato = new Contrato();
		 contrato.setId(1);
		 contrato.setEstado(true);
		 contrato.setDescuento(0);
		 contrato.setCliente(cliente);
		 contrato.setPlan(plan);
		 
		 List<Contrato> contratoList = Arrays.asList(contrato);
		 when(contratoServiceTest.findAll()).thenReturn(contratoList);
		 
		 this.mockMvc
		 .perform(get("/api/v1/contratos").contentType(MediaType.APPLICATION_JSON))
	       .andDo(print())
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$.[0].id").value(1));
	 }
	 @Test
	  void shouldReturnNotFoundInFindAll() throws Exception {

		 this.mockMvc
		 	.perform(get("/api/v1/contratos").contentType(MediaType.APPLICATION_JSON))
		 	.andDo(print())
		 	.andExpect(status().isNotFound());
	 }
	 
	 
	 @Test
	  void shouldRegisterANewContract() throws Exception {
		 
		 Contrato contrato1 = 
					TestUtil.loadObjectFromResource(
							"contracts/response/contracts_entity.json", Contrato.class);
		 when(clienteServiceTest.existeCliente(anyInt())).thenReturn(true);
		 when(planServiceTest.existePlan(anyInt())).thenReturn(true);
		 when(contratoServiceTest.existeClienteConContrato(anyInt())).thenReturn(true);
		 when(contratoServiceTest.save(any(Contrato.class))).thenReturn(contrato1);
		 
		 
		   this.mockMvc
	        .perform(
	            post("/api/v1/saveContrato")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(TestUtil.toJson(contrato1)))
	        .andDo(print())
	        .andExpect(status().isCreated());
	 }

	 @Test
	  void shouldReturnNotFoundInSaveContratos() throws Exception {
		 Contrato contrato1 = 
					TestUtil.loadObjectFromResource(
							"contracts/response/contracts_entity.json", Contrato.class);
		 when(clienteServiceTest.existeCliente(anyInt())).thenReturn(false);
		 when(planServiceTest.existePlan(anyInt())).thenReturn(false);

		   this.mockMvc
	        .perform(
	            post("/api/v1/saveContrato")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(TestUtil.toJson(contrato1)))
	        .andDo(print())
	        .andExpect(status().isNotFound());
	 }
	 
	 @Test
	  void shouldReturnListOfContractsByIdClient() throws Exception {
		 Cliente cliente = new Cliente();
		 cliente.setId(1);
		 cliente.setNombre("firstName");
		 cliente.setApellido("lastName");
		 cliente.setRun("1-1");
		 cliente.setDireccion("adress");
		 SimpleDateFormat formato = new SimpleDateFormat("yyyy/mm/dd");
		 Date fecha=formato.parse("1990/05/15");
		 cliente.setFechaNacimiento(fecha);
		 cliente.setEstado(true);

		 Plan plan = new Plan();
		 plan.setId(1);
		 plan.setNombre("planName");
		 plan.setPrecio(100);
		 plan.setServicio("servicioDePlan");
		 plan.setEstado(true);
		 
		 Contrato contrato = new Contrato();
		 contrato.setId(1);
		 contrato.setEstado(true);
		 contrato.setDescuento(0);
		 contrato.setCliente(cliente);
		 contrato.setPlan(plan);
		 
		 List<Contrato> contratoList = Arrays.asList(contrato);
		 when(contratoServiceTest.listaContratoByIdCliente(anyInt())).thenReturn(contratoList);
		 this.mockMvc
		 .perform(
				 get("/api/v1/contratosByIdCliente/{id}",1)
				 .contentType(MediaType.APPLICATION_JSON))
	       .andDo(print())
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$.[0].id").value(1));
	 }
	 
	 @Test
	  void shouldReturnNotFoundInFindAllByCliente() throws Exception {

		 this.mockMvc
		 	.perform(
					 get("/api/v1/contratosByIdCliente/{id}",1)
					 .contentType(MediaType.APPLICATION_JSON))
		 	.andDo(print())
		 	.andExpect(status().isNotFound());
	 }


}
