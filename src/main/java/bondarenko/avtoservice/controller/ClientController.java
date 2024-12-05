package bondarenko.avtoservice.controller;

import bondarenko.avtoservice.model.Client;
import bondarenko.avtoservice.service.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ClientController {

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, String> fullNameColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField phoneField;


    private ClientService clientService;

    @FXML
    public void initialize() {
        loadClients();
    }

    private void loadClients() {
        clientTable.getItems().clear();
        clientTable.getItems().addAll(clientService.getAllClients());
    }

    @FXML
    public void addClient() {
        Client client = new Client();
        client.setFullName(fullNameField.getText());
        client.setPhone(phoneField.getText());
        clientService.saveClient(client);
        loadClients();
    }
}
