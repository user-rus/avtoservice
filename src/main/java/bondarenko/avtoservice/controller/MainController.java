package bondarenko.avtoservice.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final ConfigurableApplicationContext context;

    @FXML
    private Button viewRecordsButton;

    @FXML
    private Button scheduleServiceButton;

    @FXML
    private Button manageClientsButton;

    @FXML
    private Button manageServicesButton;

    @FXML
    private Button exitApplicationButton;


    @FXML
    public void initialize() {
        // Инициализация, если необходимо
    }


    @FXML
    public void viewRecords() {
        log.info("Просмотр записей");
        // Логика для просмотра записей
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/appointments.fxml"));
            loader.setControllerFactory(context::getBean);

            Parent root = loader.load();

            Stage stage =  (Stage) viewRecordsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Запись на обслуживание");
            stage.show();
        } catch (IOException e) {
            log.error("Ошибка при открытии окна запись на обслуживание", e);
        }
    }

    @FXML
    public void scheduleService() {
        log.info("Запись на обслуживание");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/appointment.fxml"));
            loader.setControllerFactory(context::getBean);

            Parent root = loader.load();

            Stage stage =  (Stage) scheduleServiceButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Запись на обслуживание");
            stage.show();
        } catch (IOException e) {
            log.error("Ошибка при открытии окна запись на обслуживание", e);
        }
    }

    @FXML
    public void manageClients() {
        log.info("Управление клиентами");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageClients.fxml"));
            loader.setControllerFactory(context::getBean);

            Parent root = loader.load();

            Stage stage =  (Stage) manageClientsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Управление клиентами");
            stage.show();
        } catch (IOException e) {
            log.error("Ошибка при открытии окна управления клиентами", e);
        }
    }

    @FXML
    public void manageServices() {
        log.info("Управление услугами");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageServices.fxml"));
            loader.setControllerFactory(context::getBean);

            Parent root = loader.load();

            Stage stage =  (Stage) manageServicesButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Управление улугами");
            stage.show();
        } catch (IOException e) {
            log.error("Ошибка при открытии окна управления улугами", e);
        }
    }

    @FXML
    public void exitApplication() {
        log.info("Выход из приложения");
        Stage stage = (Stage) exitApplicationButton.getScene().getWindow();
        stage.close();
    }

}
