package bondarenko.avtoservice.service;

import bondarenko.avtoservice.model.Amenities;
import bondarenko.avtoservice.repository.AmenitiesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AmenitiesService {

    private final AmenitiesRepository amenitiesRepository;

    public List<Amenities> getAllServices() {
        return amenitiesRepository.findAll();
    }

    public void saveAmenities(Amenities amenities) {
        amenitiesRepository.save(amenities);
    }

    public void deleteAmenities(Amenities amenities) {
        amenitiesRepository.delete(amenities);
    }
}
