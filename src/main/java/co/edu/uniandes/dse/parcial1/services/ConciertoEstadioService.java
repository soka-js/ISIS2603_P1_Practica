package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

    // Complete

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public ConciertoEntity crearRelacionConciertoEstadio(Long conciertoId, Long estadioId) throws Exception, IllegalOperationException {
       ConciertoEntity concierto = conciertoRepository.findById(conciertoId)
       .orElseThrow(() -> new Exception("No se encontro un concierto con ese id"));

       EstadioEntity estadio = estadioRepository.findById(estadioId)
       .orElseThrow(() -> new Exception("No se encontro un estadio con ese id"));

       if (concierto.getAforo() > estadio.getAformoMaximo()) {
        throw new IllegalOperationException("Aforro concierto mas que aforo estadio");
       }

       if (estadio.getPrecioAlquiler() > concierto.getPresupuesto()) {
        throw new IllegalOperationException("Precio alquiler estadio ams que presupuesto concierto");
       }

       List<ConciertoEntity> conciertosExistentes = estadio.getConciertos();

        for (ConciertoEntity existente : conciertosExistentes) {
            Duration duration = Duration.between(existente.getFecha(), concierto.getFecha());
            long diasDeDiferencia = Math.abs(duration.toDays());
            
        if (diasDeDiferencia < 2) {
            throw new IllegalOperationException("Debe existir un tiempo mínimo de 2 días entre los conciertos del mismo estadio.");
            }
        }

       concierto.setEstadio(estadio);
       return concierto;
    }


}
