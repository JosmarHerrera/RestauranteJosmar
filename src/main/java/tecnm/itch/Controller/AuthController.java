package tecnm.itch.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable; // <-- IMPORTANTE
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tecnm.itch.DTO.AuthResponseDto;
import tecnm.itch.DTO.LoginRequest;
import tecnm.itch.DTO.RegisterRequest;
import tecnm.itch.Entity.Usuario; // <-- IMPORTANTE
import tecnm.itch.Repository.UsuarioRepository;
import tecnm.itch.Service.AuthService;

@CrossOrigin(origins = "https://frontjosmar-production.up.railway.app")
@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AuthService authService;

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {

		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		UserDetails userDetails = (UserDetails) auth.getPrincipal();

		// --- ¡CAMBIOS AQUÍ! ---
		// 1. Busca el usuario completo en la BD
		Usuario usuarioCompleto = usuarioRepository.findByUsername(userDetails.getUsername())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado en BD"));

		// 2. Extrae los roles
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		// 3. Pasa el ID del usuario al DTO de respuesta
		AuthResponseDto responseDto = new AuthResponseDto(userDetails.getUsername(), roles, usuarioCompleto.getId());

		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/auth/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		try {
			authService.registerNewUser(request);
			return ResponseEntity.ok("Usuario registrado exitosamente!");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// --- 🚀 ¡AQUÍ ESTÁ EL MÉTODO QUE FALTA! ---
	// Este es el endpoint que tu React está intentando llamar

	@PostMapping("/auth/register/cliente/{idCliente}")
	public ResponseEntity<?> registerUserForCliente(@RequestBody RegisterRequest request, @PathVariable int idCliente) {

		try {
			// Llamamos al NUEVO método en tu AuthService
			Usuario nuevoUsuario = authService.registerUserForCliente(request, idCliente);

			// Si todo sale bien, mandamos la respuesta
			return ResponseEntity.ok(nuevoUsuario); // O un mensaje de éxito

		} catch (RuntimeException e) {
			// Si el servicio lanzó un error (ej. "Cliente no encontrado")
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/auth/register/empleado")
	public ResponseEntity<?> registerUserForEmpleado(@RequestBody RegisterRequest request) {

		try {
			// La firma del método de servicio también cambió
			Usuario nuevoUsuario = authService.registerUserForEmpleado(request);
			return ResponseEntity.ok(nuevoUsuario); // Devuelve el usuario creado
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
