package tecnm.itch.Service.Implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tecnm.itch.DTO.ClienteDto;
import tecnm.itch.Entity.Cliente;
import tecnm.itch.Mapper.ClienteMapper;
import tecnm.itch.Repository.ClienteRepository;
import tecnm.itch.Service.ClienteService;

@Service
@AllArgsConstructor
public class ClienteServiceImplement implements ClienteService {

	private ClienteRepository clienteRepository;

	@Override
	public ClienteDto createCliente(ClienteDto clienteDto) {

		Cliente cliente = ClienteMapper.mapToCliente(clienteDto);
		Cliente savedCliente = clienteRepository.save(cliente);

		return ClienteMapper.mapToClienteDto(savedCliente);
	}

	@Override
	public ClienteDto getClienteById(Integer id_cliente) {
		Cliente cliente = clienteRepository.findById(id_cliente).orElse(null);
		return ClienteMapper.mapToClienteDto(cliente);
	}

	@Override
	public List<ClienteDto> getAllCliente() {
		List<Cliente> clientes = clienteRepository.findAll();
		return clientes.stream().map((cliente) -> ClienteMapper.mapToClienteDto(cliente)).collect(Collectors.toList());
	}

	@Override
	public ClienteDto updateCliente(Integer id_cliente, ClienteDto updateCliente) {

		// Buscar el registro a modificar
		Cliente cliente = clienteRepository.findById(id_cliente).orElse(null);

		// Modificar los atribuitos
		cliente.setNombreCliente(updateCliente.getNombreCliente());
		cliente.setTelefonoCliente(updateCliente.getTelefonoCliente());
		cliente.setCorreoCliente(updateCliente.getCorreoCliente());

		// Guardar cambios
		Cliente updateClienteObj = clienteRepository.save(cliente);

		return ClienteMapper.mapToClienteDto(updateClienteObj);
	}

	@Override
	public void deleteCliente(Integer id_cliente) {
		// Buscar el registro que se desea eliminar
		Cliente cliente = clienteRepository.findById(id_cliente).orElse(null);
		clienteRepository.deleteById(id_cliente);
	}

}
