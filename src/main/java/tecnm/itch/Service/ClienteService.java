package tecnm.itch.Service;

import java.util.List;

import tecnm.itch.DTO.ClienteDto;

public interface ClienteService {
	ClienteDto createCliente(ClienteDto clienteDto);

	// Buscar cliente por id
	ClienteDto getClienteById(Integer id_cliente);

	// Obtener todos los clientes
	List<ClienteDto> getAllCliente();

	// Construir REST API para actualizar clientes
	ClienteDto updateCliente(Integer id_cliente, ClienteDto updateCliente);

	// Construir DELETE REST API para borrar cliente
	void deleteCliente(Integer id_cliente);
}
