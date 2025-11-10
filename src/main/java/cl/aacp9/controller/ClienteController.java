package cl.aacp9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.aacp9.model.Cliente;
import cl.aacp9.service.IClienteService;

@RestController
@RequestMapping("api/v1")
public class ClienteController {
	
	@Autowired
	public IClienteService clienteService;
	
	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> findAll(){
		try {
			List<Cliente> listaClientes = clienteService.findAll();
			if(!listaClientes.isEmpty()) {
				return new ResponseEntity<>(listaClientes,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
				
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/disableClient/{id}")
	public ResponseEntity<Cliente> disableClient(@PathVariable Integer id, @RequestBody Cliente clienteActualizado){
		try {
			Cliente cliente=clienteService.disableClient(id, clienteActualizado);
			return new ResponseEntity<>(cliente,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
