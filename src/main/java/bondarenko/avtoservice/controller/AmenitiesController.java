package bondarenko.avtoservice.controller;

import bondarenko.avtoservice.model.Amenities;
import bondarenko.avtoservice.service.AmenitiesService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AmenitiesController { // Контроллер для работы с AmenitiesService

    private final AmenitiesService amenitiesService;
    private final ConfigurableApplicationContext context;

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
        Amenities аmenities = new Amenities();
        аmenities.setAmenitiesName(amenitiesNameField.getText());
        аmenities.setDescription(descriptionField.getText());
        аmenities.setPrice(Double.parseDouble(priceField.getText()));
        amenitiesService.saveAmenities(аmenities);
        loadServices();
    }

    @FXML
    public void editAmenities() {
        Amenities selectedAmenity = amenitiesTable.getSelectionModel().getSelectedItem();
        if (selectedAmenity != null) {
            selectedAmenity.setAmenitiesName(amenitiesNameField.getText());
            selectedAmenity.setDescription(descriptionField.getText());
            selectedAmenity.setPrice(Double.parseDouble(priceField.getText()));
            amenitiesService.saveAmenities(selectedAmenity);
            loadServices();
        } else {
            log.warn("Не выбрана услуга для редактирования");
        }
    }

    @FXML
    public void deleteAmenities() {
        Amenities selectedAmenity = amenitiesTable.getSelectionModel().getSelectedItem();
        if (selectedAmenity != null) {
            amenitiesService.deleteAmenities(selectedAmenity);
            loadServices();
        } else {
            log.warn("Не выбрана услуга для удаления");
        }
    }

    @FXML
    public void goBack() {
        try {
            // Загружаем FXML файл главного экрана
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            loader.setControllerFactory(context::getBean); // Используем Spring для создания контроллера
            Parent root = loader.load();
            Stage stage = (Stage) amenitiesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Автосервис");
            stage.show();
        } catch (IOException e) {
            log.error("Ошибка при загрузке главного экрана: ", e);
        }
    }

}
