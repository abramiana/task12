package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Представляє планету у системі.
 */
@Data
@Entity
@Table(name = "planet")
public class Planet {
    @Id
    @Pattern(regexp = "[A-Z0-9]+")
    private String id;

    @Size(min = 1, max = 500)
    private String name;
}