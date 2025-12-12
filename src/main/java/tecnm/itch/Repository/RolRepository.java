package tecnm.itch.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tecnm.itch.Entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
	Optional<Rol> findByNombre(String nombre);
}
