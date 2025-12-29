package cl.aacp9.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("/api/v1")
public class HomeController {
	 @GetMapping("/hello")
	    public String hello(Authentication auth) {
	        return "Hello "+auth.getName();
	    }

}
