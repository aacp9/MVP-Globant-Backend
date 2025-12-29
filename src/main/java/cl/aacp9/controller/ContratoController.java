package cl.aacp9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.aacp9.model.Contrato;
import cl.aacp9.service.IClienteService;
import cl.aacp9.service.IContratoService;
import cl.aacp9.service.IPlanService;

@RestController
@RequestMapping("api/v1/contrato")
public class ContratoController {

	@Autowired
	public IContratoService contratoService;
	@Autowired
	public IClienteService clienteService;
	@Autowired
	public IPlanService planService;
	
	@GetMapping("user/contratos")
	public ResponseEntity<List<Contrato>> findAll(){
		List<Contrato> listaContratos= contratoService.findAll();
		if(!listaContratos.isEmpty()) {
			return new ResponseEntity<>(listaContratos,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("user/contratosByIdCliente/{id}")
	public ResponseEntity<List<Contrato>> findAllByCliente(@PathVariable Integer id){
		List<Contrato> listaContratosByIdCliente=contratoService.listaContratoByIdCliente(id);  
		if(!listaContratosByIdCliente.isEmpty()) {
				return new ResponseEntity<>(listaContratosByIdCliente,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("user/saveContrato")
	public ResponseEntity<Contrato> saveContrato(@RequestBody Contrato contrato){
		if (contratoService.existeClienteConContrato(contrato.getCliente().getId())){
			//aplicar descuento de 5% al plan elejido
			contrato.setDescuento(5);
		}
		Contrato resultado=contratoService.create(contrato);
		if (resultado!=null) {
			return new ResponseEntity<>(contrato,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("admin/deleteContrato/{id}")
	public ResponseEntity<Void> deleteContrato(@PathVariable Integer id){
		if(!contratoService.existeContrato(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		contratoService.deleteContrato(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
}
