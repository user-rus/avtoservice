package bondarenko.avtoservice.repository;

import bondarenko.avtoservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByCar_Client_fullName(String fullName);
}
