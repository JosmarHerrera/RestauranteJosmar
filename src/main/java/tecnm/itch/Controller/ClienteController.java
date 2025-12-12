package tecnm.itch.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tecnm.itch.DTO.ClienteDto;
import tecnm.itch.Service.ClienteService;

@CrossOrigin("*")

@AllArgsConstructor
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	// CONSTRUIR EL REST API PARA AGREGAR UN CLIENTE
	@PostMapping
	public ResponseEntity<ClienteDto> crearCliente(@RequestBody ClienteDto clienteDto) {
		ClienteDto guardarCliente = clienteService.createCliente(clienteDto);
		return new ResponseEntity<>(guardarCliente, HttpStatus.CREATED);
	}

	// Construir GET del cliente REST API
	@GetMapping("{id}")
	public ResponseEntity<ClienteDto> getClienteById(@PathVariable("id") Integer id_cliente) {
		ClienteDto clienteDto = clienteService.getClienteById(id_cliente);
		return ResponseEntity.ok(clienteDto);
	}

	// REST .API Construir el GET para todos los clientes
	@GetMapping
	public ResponseEntity<List<ClienteDto>> getAllClientes() {
		List<ClienteDto> clientes = clienteService.getAllCliente();
		return ResponseEntity.ok(clientes);
	}

	// Construir REST API Update Cliente.
	// Exponer un endpoint HTTP PUT para actualizar un cliente
	@PutMapping("{id}")
	public ResponseEntity<ClienteDto> updateCliente(@PathVariable("id") Integer id_cliente,
			@RequestBody ClienteDto updateCliente) {
		ClienteDto clienteDto = clienteService.updateCliente(id_cliente, updateCliente);
		return ResponseEntity.ok(clienteDto);
	}

	// Construir el DELETE Cliente para eliminar un cliente
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCliente(@PathVariable("id") Integer id_cliente) {
		clienteService.deleteCliente(id_cliente);
		return ResponseEntity.ok("Cliente eliminado.");
	}
}
