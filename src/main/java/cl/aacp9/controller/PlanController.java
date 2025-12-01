package cl.aacp9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.aacp9.model.Plan;
import cl.aacp9.service.IPlanService;

@RestController
@RequestMapping("api/v1")
public class PlanController {
	
	@Autowired
	public IPlanService planService;
	
	@GetMapping("/planes")
	public ResponseEntity<List<Plan>> findAll(){
			List<Plan> listaPlanes = planService.findAll();
			if(!listaPlanes.isEmpty()) {
				return new ResponseEntity<>(listaPlanes,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	}

	@PutMapping("/disablePlan/{id}")
	public ResponseEntity<Plan> disablePlan(@PathVariable Integer id, @RequestBody Plan planActualizado){
			Plan plan = planService.disablePlan(id, planActualizado);
			return new ResponseEntity<>(plan,HttpStatus.OK);
	}
}
