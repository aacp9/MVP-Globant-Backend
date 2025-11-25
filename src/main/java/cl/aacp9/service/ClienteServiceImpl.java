package cl.aacp9.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.aacp9.model.Cliente;
import cl.aacp9.repository.IClienteRepository;

@Service
public class ClienteServiceImpl implements IClienteService{
	@Autowired
	private IClienteRepository clienteRepository;
	
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente disableClient(Integer id, Cliente clienteActualizado) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con el ID: " + id));
        clienteExistente.setEstado(clienteActualizado.getEstado());
        return clienteRepository.save(clienteExistente);
	}

	@Override
	public Boolean existeCliente(Integer id) {
		
		return clienteRepository.existsById(id);
	}
	

}
