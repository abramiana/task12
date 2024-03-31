package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * Представляє клієнта компанії.
 */
@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 200)
    private String name;
}