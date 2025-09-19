package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

    // Complete

    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public EstadioEntity crearEstadio(EstadioEntity estadio) throws IllegalOperationException {
        if (estadio.getNombreCiudad().length() <= 2) {
            throw new IllegalOperationException("Nombre de la ciudad corto (menor que 3 letras)");
        }

        if (estadio.getAformoMaximo() <= 1000 || estadio.getAformoMaximo() > 1000000 ) {
            throw new IllegalOperationException("Aformo maximo del estadio invalido");
        }

        if (estadio.getPrecioAlquiler() <= 100000) {
            throw new IllegalOperationException("Precio del alquiler muy bajo");
        }

        return estadioRepository.save(estadio);
    }

}
