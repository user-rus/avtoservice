package bondarenko.avtoservice.service;


import bondarenko.avtoservice.model.Appointment;
import bondarenko.avtoservice.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public List<Appointment> findAppointmentsByClientFullName(String fullName) {
        return appointmentRepository.findByCar_Client_fullName(fullName);
    }
}
