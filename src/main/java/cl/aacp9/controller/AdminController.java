package cl.aacp9.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.aacp9.model.User;
import cl.aacp9.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {//este controlador manejará cualquier solicitud destinada únicamente a los administradores

	private final UserService userService;
	
	 @GetMapping("/get-all-users")//buena practica usar guiones para esta URL
	    public ResponseEntity<?> getAllUsers() {
	        List<User> userList = userService.getAllUsers();

	        if(!userList.isEmpty()) {
	            return new ResponseEntity<>(userList, HttpStatus.OK);
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
}
