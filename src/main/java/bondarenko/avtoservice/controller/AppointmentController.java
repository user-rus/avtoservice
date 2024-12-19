package bondarenko.avtoservice.controller;

import bondarenko.avtoservice.model.*;
import bondarenko.avtoservice.service.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AppointmentController {

    private final ConfigurableApplicationContext context;
    private final AppointmentService appointmentService;
    private final AmenitiesService amenitiesService;
    private final ClientService clientService;
    private final CarService carService;
    private final EmployeeService employeeService;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private ComboBox<Car> carComboBox;

    @FXML
    private ComboBox<Amenities> amenitiesComboBox;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private TextField statusField;

    @FXML
    private Button addAppointmentButton;

    @FXML
    public void initialize() {
        loadClients();
        loadAmenities();
    }

    private void loadClients() {
        List<Client> clients = clientService.getAllClients();
        clientComboBox.setItems(FXCollections.observableArrayList(clients));
        clientComboBox.setOnAction(event -> loadCarsForSelectedClient());
    }

    private void loadCarsForSelectedClient() {
        Client selectedClient = clientComboBox.getValue();
        if (selectedClient != null) {
            List<Car> cars = carService.findAllByClient(selectedClient);
            carComboBox.setItems(FXCollections.observableArrayList(cars));
        } else {
            carComboBox.getItems().clear();
        }
    }

    private void loadAmenities() {
        List<Amenities> amenities = amenitiesService.getAllServices();
        amenitiesComboBox.setItems(FXCollections.observableArrayList(amenities));
    }

    @FXML
    public void addAppointment() {
        Client selectedClient = clientComboBox.getValue();
        Car selectedCar = carComboBox.getValue();
        Amenities selectedAmenities = amenitiesComboBox.getValue();
        LocalDateTime appointmentDateTime = appointmentDatePicker.getValue().atStartOfDay(); // Пример, можно изменить на выбор времени
        String status = statusField.getText();

        if (selectedClient != null && selectedCar != null && selectedAmenities != null && appointmentDateTime != null && !status.isEmpty()) {
            Appointment appointment = new Appointment();
            appointment.setCar(selectedCar);
            appointment.setAmenities(selectedAmenities);
            appointment.setEmployee(employeeService.getAllEmployees().getFirst());
            appointment.setAppointmentDateTime(appointmentDateTime);
            appointment.setStatus(status);


            appointmentService.saveAppointment(appointment);
            log.info("Запись на обслуживание добавлена: {}", appointment);
            loadAppointments(); // Обновление таблицы
        } else {
            log.warn("Не все поля заполнены!");
        }
    }

    private void loadAppointments() {
        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(appointmentService.getAllAppointments());
    }


    @FXML
    public void goBack() {
        try {
            // Загружаем FXML файл главного экрана
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            loader.setControllerFactory(context::getBean); // Используем Spring для создания контроллера
            Parent root = loader.load();

            // Получаем текущее окно и устанавливаем новую сцену
            Stage stage = (Stage) appointmentTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Автосервис");
            stage.show();
        } catch (IOException e) {
            log.error("Ошибка при загрузке главного экрана: ", e);
        }
    }
}