package co.edu.uniandes.dse.parcial1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.services.ConciertoService;

@RestController
@RequestMapping("/conciertos")
public class ConciertoController {

    @Autowired
    private ConciertoService conciertoService;

    /**
     * Crea un nuevo concierto en la base de datos.
     * @param concierto El objeto ConciertoEntity que se va a crear.
     * @return El ConciertoEntity recién creado.
     * @throws IllegalOperationException Si se viola alguna regla de negocio.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ConciertoEntity create(@RequestBody ConciertoEntity concierto) throws IllegalOperationException {
        return conciertoService.creacionConcierto(concierto);
    }
}
