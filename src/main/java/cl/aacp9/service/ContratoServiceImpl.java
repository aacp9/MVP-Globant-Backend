package cl.aacp9.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.aacp9.model.Contrato;
import cl.aacp9.repository.IContratoRepository;

@Service
public class ContratoServiceImpl implements IContratoService {
	@Autowired
	private IContratoRepository contratoRepository;

	public List<Contrato> findAll(){
		return contratoRepository.findAll();
	}
	@Override
	public void save(Contrato contrato) {
		contratoRepository.save(contrato);
		
	}
	@Override
	public List<Contrato> listaContratoByIdCliente(Integer id) {
		try {
			List<Contrato> listaContratos = contratoRepository.findAll();
			List<Contrato> listaContratoById = new ArrayList<Contrato>();
			if(!listaContratos.isEmpty()) {
				for (Contrato contrato : listaContratos) {
					if(contrato.getCliente().getId()==id) {
						listaContratoById.add(contrato);
					}
					return listaContratoById;
				}
				return null;
			}else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean existeClienteConContrato(Integer id) {
		try {
			List<Contrato> contratoList = contratoRepository.findAll();
			if(!contratoList.isEmpty()) {
				for (Contrato contrato : contratoList) {
					//verifico que el cliente exista, este habilitado y tenga un plan activo.
					if(contrato.getCliente().getId()==id && contrato.getCliente().getEstado() && contrato.getPlan().getEstado()) {
						System.out.println("###############################");
						System.out.println("Valor de id cliente: "+contrato.getCliente().getId());
						System.out.println("Valor de id estado cliente: "+contrato.getCliente().getEstado());
						System.out.println("Valor de id estado plan: "+contrato.getPlan().getEstado());
						System.out.println("###############################");
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	
}
