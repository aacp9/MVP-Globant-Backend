package cl.aacp9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cl.aacp9.exception.ApiException;
import cl.aacp9.model.Plan;
import cl.aacp9.repository.IContratoRepository;
import cl.aacp9.repository.IPlanRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j //para uso del log
@Service
public class PlanServiceImpl implements IPlanService {
	@Autowired
	private IPlanRepository planRepository; 
	@Autowired
	private IContratoRepository contratoRepository;
	
	public List<Plan> findAll(){
		try {
			return planRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("error al listar planes",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public Plan disablePlan(Integer id, Plan planActualizado) {
		try {
			Plan planExistente = planRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Plan no encontrado con el ID: " + id));
			planExistente.setEstado(planActualizado.getEstado());
			return planRepository.save(planExistente);
		} catch (Exception e) {
			log.error("error al deshabilitar plan error "+ e.getMessage());
			throw new ApiException("error al deshabilitar plan throw",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean existePlan(Integer id) {
		try {
			return planRepository.existsById(id);
		} catch (Exception e) {
			log.error("Error en existePlan"+e.getMessage());
			throw new ApiException("error comprobar existencia de plan",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional
	public Plan create(Plan plan) {
		try {
			return planRepository.save(plan);
		} catch (Exception e) {
			log.error("Error en create: "+e.getMessage());
			throw new ApiException("error al isertar datos",HttpStatus.BAD_REQUEST);
		}	
	}

	@Override
	public void deletePlan(Integer id) {
		try {
			Boolean exist = contratoRepository.existByPlanId(id);
			
			if(exist != null && exist) {
				contratoRepository.deleteContratoByIdPlan(id);
			}
			planRepository.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("Empty", HttpStatus.NOT_FOUND);
		}
		
	}
}
