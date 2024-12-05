package bondarenko.avtoservice.repository;

import bondarenko.avtoservice.model.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {
}
