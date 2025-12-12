package tecnm.itch.Mapper;

import tecnm.itch.DTO.ClienteDto;
import tecnm.itch.Entity.Cliente;

public class ClienteMapper {

	public static ClienteDto mapToClienteDto(Cliente cliente) {

		if (cliente == null) {
			return null;
		}

		return new ClienteDto(cliente.getId_cliente(), cliente.getNombreCliente(), cliente.getTelefonoCliente(),
				cliente.getCorreoCliente());
	}

	public static Cliente mapToCliente(ClienteDto clienteDto) {

		if (clienteDto == null) {
			return null;
		}

		Cliente cliente = new Cliente();

		cliente.setId_cliente(clienteDto.getId_cliente());
		cliente.setNombreCliente(clienteDto.getNombreCliente());
		cliente.setTelefonoCliente(clienteDto.getTelefonoCliente());
		cliente.setCorreoCliente(clienteDto.getCorreoCliente());

		return cliente;
	}

}