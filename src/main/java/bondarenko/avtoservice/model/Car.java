package bondarenko.avtoservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Override
    public String toString() {
        return brand;
    }
}
