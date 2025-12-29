package cl.aacp9.config;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfig {
	
	//firma y verifica tokens JSON Web Tokens (JWT) en una aplicación de seguridad, 
	//asegurando que solo el servidor tenga acceso a esa clave privada 
	//para la criptografía asimétrica. 
	@Value("${security.jwt.private-key}")
    private RSAPrivateKey privateKey;

    @Value("${security.jwt.public-key}")
    private RSAPublicKey publicKey;

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withPublicKey(publicKey)
                .build();
    }
    
    @Bean
    public JwtEncoder jwtEncoder() {
        RSAKey rsa = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();
        JWKSource<SecurityContext> jwtks = new ImmutableJWKSet<>(new JWKSet(rsa));
        return new NimbusJwtEncoder(jwtks);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    //con este metodo tomamos el token retornado de la validación de usuario y contraseña
    //para luego extraer los distintos datos que trae
//    {
//	  "iss": "aacp9",
//	  "sub": "admin",
//	  "exp": 1766064484,
//	  "iat": 1766063584,
//	  "roles": [
//	    "ROLE_ADMIN"
//	  ]
//	}
    
    @Bean
    public JwtAuthenticationConverter jwtAuthConverter() {
        // 1. Create a JwtAuthenticationConverter instance
        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();

//        El operador lambda -> en Java, usado en Spring Boot, 
//        permite definir funciones anónimas concisas, 
//        ideal para operaciones en colecciones con Streams 
//        (métodos como filter, map, forEach), configuraciones 
//        funcionales (como en @Service con Supplier o Function), 
//        y manejadores de eventos, reemplazando clases anónimas 
//        para código más legible, como lista.stream().
//        filter(p -> p.getEdad() > 18).collect(Collectors.toList());
//        
        
        // 2. Tell it exactly how to pull the roles out of the token
        //Dígale exactamente cómo extraer los roles del token
        conv.setJwtGrantedAuthoritiesConverter(
                jwt -> {
                    // Grab the 'roles' claim as a list of plain string
                	//Tome la afirmación(claim) 'roles' como una lista de cadenas simples
                	//es 'roles' ver clase JwtService metodo 'generateToken' en .claim
                    List<String> roles = jwt.getClaimAsStringList("roles");//llamamos al nombre de la afirmacion "roles"

                    // turn each string into Spring authority
                    //luego Convierte cada cadena en autoridad de Spring
                    //usamos la API de Transmision (.strem)
                    return roles.stream()
                            .map(SimpleGrantedAuthority::new) //asignamos cada cadena de rol a una nueve aurotidad simple
                            .collect(Collectors.toList());
                }
        );

        // 3. Return the fully configured converter so Spring can use it.
        //Devuelve el convertidor completamente configurado para que Spring pueda usarlo.
        return conv;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth-> auth
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/v1/cliente/user/**").hasRole("USER")
                                .requestMatchers("/api/v1/cliente/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/plan/user/**").hasRole("USER")
                                .requestMatchers("/api/v1/plan/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/contrato/user/**").hasRole("USER")
                                .requestMatchers("/api/v1/contrato/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).oauth2ResourceServer(
                        rs -> rs.jwt(Customizer.withDefaults())
                ).sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

}
