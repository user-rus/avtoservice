package bondarenko.avtoservice.controller;

import bondarenko.avtoservice.service.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MainController {

    private final ClientService clientService;

    @FXML
    private Button addClientButton;

    @FXML
    public void initialize() {
        // Инициализация, если необходимо
    }

    @FXML
    public void addClient() {
        // Логика для открытия окна добавления клиента
        log.info("Открытие окна добавления клиента");
        // Здесь вы можете открыть новое окно или сцену для добавления клиента
    }
}
