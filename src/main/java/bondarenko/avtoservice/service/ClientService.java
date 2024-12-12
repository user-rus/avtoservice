package bondarenko.avtoservice.service;

import bondarenko.avtoservice.model.Client;
import bondarenko.avtoservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void saveClient(Client client) {
        log.info("Saving client {}", client);
        clientRepository.save(client);
    }
    public void deleteClient(Client client) {
        log.info("Deleting client {}", client);
        clientRepository.delete(client);
    }
}
