package bondarenko.avtoservice.controller;
import bondarenko.avtoservice.model.Car;
import bondarenko.avtoservice.model.Client;
import bondarenko.avtoservice.service.CarService;
import bondarenko.avtoservice.service.ClientService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
public class ClientController {

    private final ClientService clientService;
    private final CarService carService;
    private final ConfigurableApplicationContext context;

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, String> fullNameColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private TableColumn<Client, String> carsColumn;


    @FXML
    private TextField fullNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    private TextField brandField; // Поле для ввода марки автомобиля
    @FXML
    private TextField licensePlateField; // Поле для ввода номера автомобиля

    @FXML
    public void initialize() {
        if (clientService == null) {
            log.error("ClientService is not initialized!");
            return;
        }
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        carsColumn.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();
            return new SimpleStringProperty(client.getCars().stream()
                    .map(Car::getBrand) // Или любой другой атрибут, который вы хотите отобразить
                    .collect(Collectors.joining(", "))); // Объединяем марки автомобилей в строку
        });
        loadClients();
        loadClientsIntoComboBox();
    }

    @FXML
    private void loadClients() {
        log.info("Loading clients...");
        clientTable.getItems().clear();
        clientTable.getItems().addAll(clientService.getAllClients());
    }

    @FXML
    private void loadClientsIntoComboBox() {
        clientComboBox.getItems().clear();
        clientComboBox.getItems().addAll(clientService.getAllClients()
                .stream()
                .map(c -> c.getFullName())
                .collect(Collectors.toList()));
    }

    @FXML
    public void addClient() {
        if (clientService == null) {
            log.error("ClientService is not initialized!");
        }
        Client client = new Client();
        client.setFullName(fullNameField.getText());
        client.setPhone(phoneField.getText());
        clientService.saveClient(client);
        loadClients();
        loadClientsIntoComboBox();
    }
    @FXML
    public void updateClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            log.error("No client selected for update!");
            return;
        }
        selectedClient.setFullName(fullNameField.getText());
        selectedClient.setPhone(phoneField.getText());
        clientService.saveClient(selectedClient);
        loadClients();
        loadClientsIntoComboBox();
    }


    @FXML
    public void deleteClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            log.error("No client selected for deletion!");
            return;
        }

        clientService.deleteClient(selectedClient);
        loadClients();
        loadClientsIntoComboBox();
    }

    @FXML
    public void addCar() {
        Client selectedClient = clientService.getAllClients().stream()
                .filter(client -> client.getFullName().equals(clientComboBox.getValue())).findFirst().get();

        if (selectedClient == null) {
            log.error("No client selected!");
            return;
        }

        Car car = new Car();
        car.setClient(selectedClient);
        car.setBrand(brandField.getText());
        car.setLicensePlate(licensePlateField.getText());
        carService.save(car);
    }

    @FXML
    public void goBack() {
        try {
            // Загружаем FXML файл главного экрана
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            loader.setControllerFactory(context::getBean); // Используем Spring для создания контроллера
            Parent root = loader.load();

            // Получаем текущее окно и устанавливаем новую сцену
            Stage stage = (Stage) clientTable.getScene().getWindow();
            stage.setScene(new Scene(root, 960, 540));
            stage.setTitle("Автосервис");
            stage.show();
        } catch (IOException e) {
            log.error("Ошибка при загрузке главного экрана: ", e);
        }
    }
}
