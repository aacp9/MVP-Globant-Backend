package cl.aacp9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.aacp9.model.Plan;
import cl.aacp9.repository.IPlanRepository;

@Service
public class PlanServiceImpl implements IPlanService {
	@Autowired
	private IPlanRepository planRepository; 
	
	public List<Plan> findAll(){
		return planRepository.findAll();
	}

	@Override
	public Plan disablePlan(Integer id, Plan planActualizado) {
		Plan planExistente = planRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Plan no encontrado con el ID: " + id));
		planExistente.setEstado(planActualizado.getEstado());
		return planRepository.save(planExistente);
	}

	@Override
	public Boolean existePlan(Integer id) {
		return planRepository.existsById(id);
	}
}
