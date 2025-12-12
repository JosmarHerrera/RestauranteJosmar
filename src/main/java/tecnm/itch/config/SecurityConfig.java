package tecnm.itch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import tecnm.itch.Service.Implement.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
						// Endpoints públicos
						.requestMatchers("/auth/login", "/auth/register").permitAll().requestMatchers("/api/cliente")
						.permitAll() // si quieres permitir crear desde front
						.requestMatchers("/api/cliente/**").permitAll()

						// Otros recursos protegidos
						.requestMatchers("/api/productos/**").hasAnyRole("CLIENTE", "SUPERVISOR", "ADMIN", "CAJERO")
						.requestMatchers("/api/reservas/crear").hasAnyRole("CLIENTE", "CAJERO", "SUPERVISOR", "ADMIN")
						.requestMatchers("/api/empleados/**").hasAnyRole("SUPERVISOR", "ADMIN")
						.requestMatchers("/api/tipos/**", "/api/nuevo-producto/**").hasAnyRole("SUPERVISOR", "ADMIN")
						.requestMatchers("/api/ventas/**").hasAnyRole("CAJERO", "ADMIN")
						.requestMatchers("/api/reservas/**").hasAnyRole("CAJERO", "ADMIN", "SUPERVISOR")
						.requestMatchers("/api/atender/**").hasAnyRole("MESERO", "ADMIN")

						// Cualquier otra petición requiere autenticación
						.anyRequest().permitAll())
				// Login por formulario (si lo usas)
				.formLogin(form -> form.permitAll()).logout(logout -> logout.permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		 return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("http://localhost:3000");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
