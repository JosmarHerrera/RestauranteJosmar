package tecnm.itch.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tecnm.itch.Entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
