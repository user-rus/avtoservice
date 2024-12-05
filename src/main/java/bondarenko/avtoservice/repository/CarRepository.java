package bondarenko.avtoservice.repository;

import bondarenko.avtoservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
