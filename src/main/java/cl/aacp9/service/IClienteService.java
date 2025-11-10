package cl.aacp9.service;

import java.util.List;

import cl.aacp9.model.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();
	public Cliente disableClient(Integer id, Cliente clienteActualizado);
	public Boolean existeCliente(Integer id);
}
