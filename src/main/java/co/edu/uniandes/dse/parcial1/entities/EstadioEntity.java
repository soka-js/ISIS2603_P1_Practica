package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private String nombreCiudad;
    private int aformoMaximo;
    private Long precioAlquiler;

    @OneToMany(mappedBy = "estadio", fetch = FetchType.LAZY)
    private List<ConciertoEntity> conciertos = new ArrayList<>();
}
