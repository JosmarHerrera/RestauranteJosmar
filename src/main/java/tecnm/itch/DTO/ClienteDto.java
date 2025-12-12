package tecnm.itch.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClienteDto {
	@JsonProperty("id_cliente")
	private int id_cliente;

	private String nombreCliente;
	private String telefonoCliente;
	private String correoCliente;
}
