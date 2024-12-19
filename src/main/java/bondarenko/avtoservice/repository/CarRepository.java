package bondarenko.avtoservice.repository;

import bondarenko.avtoservice.model.Car;
import bondarenko.avtoservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByClient(Client client);
}
