package tecnm.itch.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tecnm.itch.Entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findByUsername(String username);
}
