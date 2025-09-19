package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Import(EstadioService.class)
public class EstadioServiceTest {
  @Autowired
  private EstadioService estadioService;

  @Autowired
  private TestEntityManager testEntityManager;

  @BeforeEach
  void setUp(){
    testEntityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
  }

  @Test
  void creacionExitosaConcierto() throws IllegalOperationException{
    EstadioEntity newEstadio = new EstadioEntity();
    newEstadio.setNombre("campin");
    newEstadio.setNombreCiudad("ATENAS");
    newEstadio.setAformoMaximo(500000);
    newEstadio.setPrecioAlquiler(300000L);

    EstadioEntity crearEstadio = estadioService.crearEstadio(newEstadio);
    assertNotNull(crearEstadio);

    EstadioEntity pruebaBD = testEntityManager.find(EstadioEntity.class, crearEstadio.getId());
    assertNotNull(pruebaBD);

    assertEquals(newEstadio.getAformoMaximo(), pruebaBD.getAformoMaximo());
    assertEquals(newEstadio.getNombre(), pruebaBD.getNombre());
    assertEquals(newEstadio.getPrecioAlquiler(), pruebaBD.getPrecioAlquiler());
 
  }

  @Test
  void falloDeCreacion() throws IllegalOperationException{
    EstadioEntity estadioInvalido = new EstadioEntity();
    estadioInvalido.setNombre("pinpin");
    estadioInvalido.setNombreCiudad("eo");
    estadioInvalido.setAformoMaximo(0);
    estadioInvalido.setAformoMaximo(500000);
    estadioInvalido.setPrecioAlquiler(300000L);

    assertThrows(IllegalOperationException.class, () -> {
      estadioService.crearEstadio(estadioInvalido);
    });

  }
}
