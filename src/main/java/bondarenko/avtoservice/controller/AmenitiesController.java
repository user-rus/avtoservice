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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.stream.Collectors;

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
        amenitiesNameColumn.setCellValueFactory(new PropertyValueFactory<>("amenitiesName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadServices();
    }

    private void loadServices() {
        amenitiesTable.getItems().clear();
        amenitiesTable.getItems().addAll(amenitiesService.getAllServices());
        log.info(amenitiesTable.getItems()
                .stream()
                .map(Amenities::getAmenitiesName)
                .collect(Collectors.joining(",")));
    }

    @FXML
    public void addAmenities() {
        if (amenitiesNameField.getText().isEmpty() ||
                descriptionField.getText().isEmpty() ||
                priceField.getText().isEmpty()) {
            log.warn("Все поля должны быть заполнены");
            return;
        }

        try {
            Amenities amenity = new Amenities();
            amenity.setAmenitiesName(amenitiesNameField.getText());
            amenity.setDescription(descriptionField.getText());
            amenity.setPrice(Double.parseDouble(priceField.getText()));
            amenitiesService.saveAmenities(amenity);
            loadServices();
            amenitiesNameField.clear();
            descriptionField.clear();
            priceField.clear();
            log.info("Услуга добавлена: " + amenity);
        } catch (NumberFormatException e) {
            log.error("Ошибка при вводе цены: " + priceField.getText(), e);
        }
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
