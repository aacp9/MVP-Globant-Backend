package cl.aacp9.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.aacp9.dto.LoginRequest;
import cl.aacp9.dto.LoginResponse;
import cl.aacp9.service.JwtService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

   private final AuthenticationManager authenticationManager;
   private final JwtService jwtService;

   @PostMapping("/login")
   public LoginResponse login(@Valid @RequestBody LoginRequest login) {
       // Authenticate the user
       Authentication auth = authenticationManager
               .authenticate(
                       new UsernamePasswordAuthenticationToken(
                               login.getUsername(),
                               login.getPassword()
                       )
               );
       // Generate the token for the authenticated user
       String token = jwtService.generateToken((UserDetails) auth.getPrincipal());
       // Return the token as response
       return new LoginResponse(token);
   }

}
