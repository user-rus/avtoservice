package bondarenko.avtoservice.controller;

import bondarenko.avtoservice.model.Amenities;
import bondarenko.avtoservice.service.AmenitiesService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class AmenitiesController { // Контроллер для работы с AmenitiesService

    @FXML
    private TableView<Amenities> amenitiesTable;

    @FXML
    private TableColumn<Amenities, String> amenitiesNameColumn;

    @FXML
    private TableColumn<Amenities, String> descriptionColumn;

    @FXML
    private TableColumn<Amenities, Double> priceColumn;

    @FXML
    private TextField amenitiesNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField priceField;

    private AmenitiesService amenitiesService;

    @FXML
    public void initialize() {
        loadServices();
    }

    private void loadServices() {
        amenitiesTable.getItems().clear();
        amenitiesTable.getItems().addAll(amenitiesService.getAllServices());
    }

    @FXML
    public void addAmenities() {
        Amenities carService = new Amenities();
        carService.setAmenitiesName(amenitiesNameField.getText());
        carService.setDescription(descriptionField.getText());
        carService.setPrice(Double.parseDouble(priceField.getText()));
        amenitiesService.saveAmenities(carService);
        loadServices();
    }
}
