package co.edu.uniandes.dse.parcial1.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private String nombreArtista;
    private LocalDateTime fecha;
    private int aforo;
    private Long presupuesto;

    @ManyToOne
    private EstadioEntity estadio;
}
