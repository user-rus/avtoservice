package bondarenko.avtoservice.controller;


import bondarenko.avtoservice.model.Amenities;
import bondarenko.avtoservice.model.Appointment;
import bondarenko.avtoservice.model.Car;
import bondarenko.avtoservice.model.Client;
import bondarenko.avtoservice.service.*;
import javafx.collections.FXCollections;
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
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AppointmentsController {

    private final ConfigurableApplicationContext context;
    private final AppointmentService appointmentService;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Car> carColumn;

    @FXML
    private TableColumn<Appointment, Amenities> amenitiesColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<Appointment, String> statusColumn;

    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {
        carColumn.setCellValueFactory(new PropertyValueFactory<>("car"));
        amenitiesColumn.setCellValueFactory(new PropertyValueFactory<>("amenities"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDateTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadAppointments();
    }

    @FXML
    public void searchByFullName() {
        String fullName = searchField.getText().trim();
        if (!fullName.isEmpty()) {
            List<Appointment> appointments = appointmentService.findAppointmentsByClientFullName(fullName);
            appointmentTable.getItems().clear();
            appointmentTable.getItems().addAll(appointments);
            log.info("Количество записей после поиска: {}", appointments.size());
        } else {
            loadAppointments(); // Если поле поиска пустое, загружаем все записи
        }
    }

    private void loadAppointments() {
        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(appointmentService.getAllAppointments());
        log.info("Количество записей: {}", appointmentService.getAllAppointments().size());
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
