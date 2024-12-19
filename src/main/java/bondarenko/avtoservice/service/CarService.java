package bondarenko.avtoservice.service;

import bondarenko.avtoservice.model.Car;
import bondarenko.avtoservice.model.Client;
import bondarenko.avtoservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> findAllByClient(Client client) {
        return carRepository.findByClient(client);
    }
}
