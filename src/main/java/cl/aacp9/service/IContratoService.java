package cl.aacp9.service;

import java.util.List;

import cl.aacp9.model.Contrato;

public interface IContratoService {
	
	public List<Contrato> findAll();
	public void save(Contrato contrato);
	public List<Contrato> listaContratoByIdCliente(Integer id);
	public Boolean existeClienteConContrato (Integer id);

}
