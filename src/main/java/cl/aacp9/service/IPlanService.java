package cl.aacp9.service;

import java.util.List;

import cl.aacp9.model.Plan;

public interface IPlanService {

	public List<Plan> findAll();
	public Plan disablePlan(Integer id, Plan planActualizado);
	public Boolean existePlan(Integer id);
}
