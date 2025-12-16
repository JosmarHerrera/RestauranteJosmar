package tecnm.itch.Service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tecnm.itch.DTO.RegisterRequest;
import tecnm.itch.Entity.Cliente;
import tecnm.itch.Entity.Rol;
import tecnm.itch.Entity.Usuario;
import tecnm.itch.Repository.ClienteRepository;
import tecnm.itch.Repository.RolRepository;
import tecnm.itch.Repository.UsuarioRepository;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ClienteRepository clienteRepository;

	public Usuario registerNewUser(RegisterRequest request) {

		if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new RuntimeException("Error: ¡El email ya está registrado!");
		}

		Usuario usuario = new Usuario();
		usuario.setUsername(request.getUsername());
		usuario.setPassword(passwordEncoder.encode(request.getPassword()));
		usuario.setActivo(true);

		Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE")
				.orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_CLIENTE' no encontrado."));

		usuario.setRoles(Collections.singleton(rolCliente));

		return usuarioRepository.save(usuario);
	}

	@Transactional
	public Usuario registerUserForCliente(RegisterRequest request, int idCliente) {

		if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new RuntimeException("Error: ¡El email ya está registrado!");
		}

		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new RuntimeException("Error: Cliente con ID " + idCliente + " no encontrado."));

		Usuario usuario = new Usuario();
		usuario.setUsername(request.getUsername());
		usuario.setPassword(passwordEncoder.encode(request.getPassword()));
		usuario.setActivo(true);

		Rol rolCliente = rolRepository.findById(1)
				.orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_CLIENTE' no encontrado."));

		usuario.setRoles(Collections.singleton(rolCliente));

		Usuario nuevoUsuario = usuarioRepository.save(usuario);

		cliente.setUsuario(nuevoUsuario);

		clienteRepository.save(cliente);

		return nuevoUsuario;
	}

	@Transactional
	public Usuario registerUserForEmpleado(RegisterRequest request) {

		// 1. Validar username
		if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new RuntimeException("Error: ¡El email ya está registrado!");
		}

		// 2. Determinar el ROL basado en el 'puesto' del request
		String puesto = request.getPuesto();
		if (puesto == null || puesto.trim().isEmpty()) {
			throw new RuntimeException("Error: El 'puesto' es requerido para asignar un rol.");
		}
		String nombreRol;

		switch (puesto) {
		case "Mesero":
			nombreRol = "ROLE_MESERO";
			break;
		case "Cajero":
		case "Cajera": // (Añado 'Cajera' por si acaso)
			nombreRol = "ROLE_CAJERO";
			break;
		case "Supervisor":
			nombreRol = "ROLE_SUPERVISOR";
			break;
		case "Administrador":
			nombreRol = "ROLE_ADMIN";
			break;
		default:
			// Puestos como "Cocinero" no tienen rol en el sistema
			throw new RuntimeException("Error: Puesto no válido para crear un usuario de sistema.");
		}

		Rol rolEmpleado = rolRepository.findByNombre(nombreRol)
				.orElseThrow(() -> new RuntimeException("Error: Rol '" + nombreRol + "' no encontrado."));

		// 3. Crear el Usuario
		Usuario usuario = new Usuario();
		usuario.setUsername(request.getUsername());
		usuario.setPassword(passwordEncoder.encode(request.getPassword()));
		usuario.setActivo(true);
		usuario.setRoles(Collections.singleton(rolEmpleado));

		// 4. Guardar y DEVOLVER el usuario (con su nuevo ID)
		return usuarioRepository.save(usuario);
	}
}