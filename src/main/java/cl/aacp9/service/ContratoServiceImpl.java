package cl.aacp9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cl.aacp9.exception.ApiException;
import cl.aacp9.model.Contrato;
import cl.aacp9.repository.IClienteRepository;
import cl.aacp9.repository.IContratoRepository;
import cl.aacp9.repository.IPlanRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j //para el logger
@Service
public class ContratoServiceImpl implements IContratoService {
	@Autowired
	private IContratoRepository contratoRepository;
	@Autowired
	private IClienteRepository clienteRepository;
	@Autowired
	private IPlanRepository planRepository;

	public List<Contrato> findAll(){
		try {
			return contratoRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("error al listar contratos",HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public Contrato create(Contrato contrato) {
		Contrato contratoResultado = null;
		try {
			Integer idCliente = contrato.getCliente().getId();
			Integer idPlan = contrato.getPlan().getId();
			boolean clienteExiste = clienteRepository.existsById(idCliente);
			boolean planExiste = planRepository.existsById(idPlan);
			boolean clienteEnabled = clienteRepository.enabledClienteById(idCliente);
			boolean planEnabled = planRepository.enabledPlanById(idPlan);
			
			if (clienteExiste && planExiste && clienteEnabled && planEnabled) {
				contratoResultado = contratoRepository.save(contrato);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("error al registrar contratos",HttpStatus.BAD_REQUEST);
		}
		return contratoResultado;
	}

	@Override
	public List<Contrato> listaContratoByIdCliente(Integer id) {
		try {
			return contratoRepository.findByIdCliente(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("error al listar contratos por id de cliente",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@Override
	public Boolean existeClienteConContrato(Integer id) {
		try {
			List<Contrato> contratoList = contratoRepository.findByIdCliente(id);
			if(!contratoList.isEmpty()) {
				for (Contrato contrato : contratoList) {
					//verifico que el cliente exista, este habilitado y tenga un plan activo.
					if(contrato.getCliente().getEstado() && contrato.getPlan().getEstado()) {
//						System.out.println("###############################");
//						System.out.println("Valor de id cliente: "+contrato.getCliente().getId());
//						System.out.println("Valor de id estado cliente: "+contrato.getCliente().getEstado());
//						System.out.println("Valor de id estado plan: "+contrato.getPlan().getEstado());
//						System.out.println("###############################");
						return true;
					}
				}
				return false;
			}
			System.out.println("VACIO LISTA DE CLIENTE");
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("error en busqueda de algun contrato en cliente",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public void deleteContrato(Integer id) {
		try {
			Boolean exist = contratoRepository.existsById(id);
			if (exist!=null && exist) {
				contratoRepository.deleteById(id);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("Empty", HttpStatus.NOT_FOUND);
		} 
	}

	@Override
	public Boolean existeContrato(Integer id) {
		try {
			return contratoRepository.existsById(id);
		} catch (Exception e) {
			log.error("Error en existeCliente"+e.getMessage());
			throw new ApiException("error comprobar existencia de cliente",HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
