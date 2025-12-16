package tecnm.itch.DTO;

import java.util.List;

public class AuthResponseDto {
	private String username;
	private List<String> roles;
	private Integer idUsuario;

	public AuthResponseDto(String username, List<String> roles, Integer idUsuario) {
		super();
		this.username = username;
		this.roles = roles;
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

}