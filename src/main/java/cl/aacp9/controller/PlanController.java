package cl.aacp9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.aacp9.model.Plan;
import cl.aacp9.service.IPlanService;

@RestController
@RequestMapping("api/v1/plan")
public class PlanController {
	
	@Autowired
	public IPlanService planService;
	
	@GetMapping("user/planes")
	public ResponseEntity<List<Plan>> findAll(){
			List<Plan> listaPlanes = planService.findAll();
			if(!listaPlanes.isEmpty()) {
				return new ResponseEntity<>(listaPlanes,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	}

	@PutMapping("user/disablePlan/{id}")
	public ResponseEntity<Plan> disablePlan(@PathVariable Integer id, @RequestBody Plan planActualizado){
			Plan plan = planService.disablePlan(id, planActualizado);
			return new ResponseEntity<>(plan,HttpStatus.OK);
	}
	
	@PostMapping("admin/savePlan")
	public ResponseEntity<Plan> registrarPlan(@RequestBody Plan plan){
		return new ResponseEntity<>(planService.create(plan), HttpStatus.CREATED);
	}
	
	@DeleteMapping("admin/deletePlan/{id}")
	public ResponseEntity<Void> deleteCliente(@PathVariable Integer id){
		if(!planService.existePlan(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		planService.deletePlan(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
