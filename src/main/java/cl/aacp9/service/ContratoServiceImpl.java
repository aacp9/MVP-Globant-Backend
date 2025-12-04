package cl.aacp9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cl.aacp9.exception.ApiException;
import cl.aacp9.model.Contrato;
import cl.aacp9.repository.IContratoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j //para el logger
@Service
public class ContratoServiceImpl implements IContratoService {
	@Autowired
	private IContratoRepository contratoRepository;

	public List<Contrato> findAll(){
		try {
			return contratoRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("error al listar contratos",HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public Contrato save(Contrato contrato) {
		Contrato contratoResultado = null;
		try {
			contratoResultado = contratoRepository.save(contrato);
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
	
	
}
