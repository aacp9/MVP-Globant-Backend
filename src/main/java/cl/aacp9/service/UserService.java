package cl.aacp9.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.aacp9.model.User;
import cl.aacp9.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Service
public class UserService implements UserDetailsService {
	
	private final UserRepository userRepository;
	

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(username)
                );
        //acá estamos devolviendo un objeto de usuario del paquete principal de Spring Security Utilies
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                //List.of(new SimpleTGrantedAuthority("USER"))    //anteriormente se definia de manera estática
                getAuthorities(user)//
        );
    }

	/*
	 * .stream() en Spring Boot se refiere a la API de Streams de Java 8, 
	 * que permite procesar colecciones de datos (Listas, Sets, etc.) 
	 * de forma declarativa y funcional, como una "tubería" de operaciones 
	 * (filtrar, mapear, reducir) para manipular elementos de forma eficiente 
	 * y concisa
	 * */
	//creamos la autoridad
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
                .toList();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
