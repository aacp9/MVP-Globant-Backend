package cl.aacp9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cl.aacp9.exception.ApiException;
import cl.aacp9.model.Cliente;
import cl.aacp9.repository.IClienteRepository;
import cl.aacp9.repository.IContratoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j //para uso del log
@Service
public class ClienteServiceImpl implements IClienteService{
	@Autowired
	private IClienteRepository clienteRepository;
	@Autowired
	private IContratoRepository contratoRepository;
	
	public List<Cliente> findAll() {
		try {
			return clienteRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("error al listar clientes",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public Cliente disableClient(Integer id, Cliente clienteActualizado) {
		try {
			log.info("Se recibió una solicitud de des/habilitación");
			log.info("id recibido : "+id);
	        Cliente clienteExistente = clienteRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con el ID: " + id));
	        clienteExistente.setEstado(clienteActualizado.getEstado());
	        return clienteRepository.save(clienteExistente);
		} catch (Exception e) {
			log.error("error al deshabilitar cliente error "+ e.getMessage());
			throw new ApiException("error al deshabilitar cliente throw",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean existeCliente(Integer id) {
		try {
			return clienteRepository.existsById(id);
		} catch (Exception e) {
			log.error("Error en existeCliente"+e.getMessage());
			throw new ApiException("error comprobar existencia de cliente",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional
	public Cliente create(Cliente cliente) {
		try {
			return clienteRepository.save(cliente);
		} catch (Exception e) {
			log.error("Error en create: "+e.getMessage());
			throw new ApiException("error al isertar datos",HttpStatus.BAD_REQUEST);
		} 
	}

	@Override
	public void deleteCliente(Integer id) {
		try {
			
			Boolean exist = contratoRepository.existByClienteId(id);
			
			if(exist != null && exist) {
				contratoRepository.deleteContratoByIdCliente(id);
			}
			clienteRepository.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("Empty", HttpStatus.NOT_FOUND);
		}
		
	}
	

}
