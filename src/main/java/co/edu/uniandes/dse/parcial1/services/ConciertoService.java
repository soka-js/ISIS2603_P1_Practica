package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    // Complete
    @Autowired
    private ConciertoRepository conciertoRepository;

    // @Autowired
    // private EstadioRepository estadioRepository;

    @Transactional
    public ConciertoEntity creacionConcierto(ConciertoEntity concierto) throws IllegalOperationException{
        if (concierto.getFecha().isBefore(LocalDateTime.now())) {
            throw new IllegalOperationException("fecha del concierto esta en el pasado");
        }

        if (concierto.getAforo() <= 10) {
            throw new IllegalOperationException("Aformo minimo invalido");
        }

        if (concierto.getPresupuesto() <= 1000) {
            throw new IllegalOperationException("Presupuesto minimo invalido");
        }

        return conciertoRepository.save(concierto);
    }

}
