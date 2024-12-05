package bondarenko.avtoservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "amenities")
public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amenities_name", nullable = false)
    private String amenitiesName;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;
}
