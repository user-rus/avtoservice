package bondarenko.avtoservice.repository;

import bondarenko.avtoservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
