package cl.aacp9.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
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
import cl.aacp9.controller.ClienteController;
import cl.aacp9.model.Cliente;
import cl.aacp9.service.IClienteService;
import cl.aacp9.util.TestUtil;
@WebMvcTest(controllers = ClienteController.class)
@ContextConfiguration(classes = {MvpGlobantApplication.class})
public class ClienteRepositoryTest {
	 @MockitoBean 
	 private IClienteService clienteServiceTest;
	 
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
		 
		 List<Cliente> clienteList = Arrays.asList(cliente);
		 when(clienteServiceTest.findAll()).thenReturn(clienteList);
		 
		 this.mockMvc
		 .perform(get("/api/v1/clientes").contentType(MediaType.APPLICATION_JSON))
	       .andDo(print())
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$.[0].id").value(1));
	 }

	 @Test
	  void shouldReturnEmpty() throws Exception {
		 
		 when(clienteServiceTest.findAll()).thenReturn(Collections.emptyList());
		 
		 this.mockMvc
		 .perform(get("/api/v1/clientes").contentType(MediaType.APPLICATION_JSON))
	       .andDo(print())
	       .andExpect(status().isNotFound());
	 }

	 
	 @Test
	  void shouldReturnClienteResponse() throws Exception {
		 Cliente clienteResponse = new Cliente();
		 clienteResponse.setId(1);
		 clienteResponse.setNombre("firstName");
		 clienteResponse.setApellido("lastName");
		 clienteResponse.setRun("1-1");
		 clienteResponse.setDireccion("adress");
		 SimpleDateFormat formato = new SimpleDateFormat("yyyy/mm/dd");
		 Date fecha=formato.parse("1990/05/15");
		 clienteResponse.setFechaNacimiento(fecha); 
		 clienteResponse.setEstado(true);
		 
		 Cliente clienteUpdate = new Cliente();
		 clienteUpdate.setId(1);
		 clienteUpdate.setNombre("firstName");
		 clienteUpdate.setApellido("lastName");
		 clienteUpdate.setRun("1-1");
		 clienteUpdate.setDireccion("adress");
		 fecha=formato.parse("1990/05/15");
		 clienteUpdate.setFechaNacimiento(fecha); 
		 clienteUpdate.setEstado(false);
 
		 when(clienteServiceTest.disableClient(anyInt(), any(Cliente.class))).
		 	thenReturn(clienteResponse);

	    this.mockMvc
        .perform(
            put("/api/v1/disableClient/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.toJson(clienteUpdate)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
	 }

	 
}
