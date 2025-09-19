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
@Import(ConciertoEstadioService.class)
public class ConciertoEstadioServiceTest {
  
  private EstadioEntity estadio;
  private ConciertoEntity concierto;

  @Autowired
  private ConciertoEstadioService conciertoEstadioService;

  @Autowired
  private TestEntityManager testEntityManager;

  @BeforeEach
  void setUp(){
    testEntityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
    testEntityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();

    // estadios y conciertos validos
    estadio = new EstadioEntity();
    estadio.setNombre("campin");
    estadio.setNombreCiudad("ATENAS");
    estadio.setAformoMaximo(500000);
    estadio.setPrecioAlquiler(101000L);
    testEntityManager.persist(estadio);

    concierto = new ConciertoEntity();
    concierto.setNombre("Word tour");
    concierto.setNombreArtista("Pink floyd");
    concierto.setAforo(35000);
    concierto.setPresupuesto(120000L);
    concierto.setFecha(LocalDateTime.now().plusDays(30));
    testEntityManager.persist(concierto);

    testEntityManager.flush();
    
  }

  @Test
  void pruebaAsociacionExitosa() throws Exception{
    ConciertoEntity resultado = conciertoEstadioService.crearRelacionConciertoEstadio(concierto.getId(), estadio.getId());

    assertNotNull(resultado);
    assertNotNull(resultado.getEstadio());
    assertEquals(estadio.getId(), resultado.getEstadio().getId());
  }

  @Test
  void pruebaEstadioNoExiste() throws Exception{
    Long conciertoIdValido = concierto.getId();
    Long estadioInvalido = 0L;

    assertThrows(Exception.class, () -> conciertoEstadioService.crearRelacionConciertoEstadio(conciertoIdValido, estadioInvalido));

  }

  @Test
  void pruebaConciertoNoExiste() throws Exception{
    Long estadioIdValido = estadio.getId();
    Long conciertoInvalido = 0L;

    assertThrows(Exception.class, () -> conciertoEstadioService.crearRelacionConciertoEstadio(conciertoInvalido, estadioIdValido));

  }

  @Test
    void testAsociacionFallaPorReglaDeFechas() {
        // 1. Arrange: Preparamos el escenario.
        
        // Creamos un concierto que ya existe en el estadio.
        ConciertoEntity conciertoExistente = new ConciertoEntity();
        conciertoExistente.setNombre("Justice");
        conciertoExistente.setAforo(500);
        conciertoExistente.setPresupuesto(500000L);
        // Le asignamos una fecha futura.
        conciertoExistente.setFecha(LocalDateTime.now().plusDays(30));
        // Lo asociamos directamente al estadio creado en el setUp().
        conciertoExistente.setEstadio(estadio);
        // Código a añadir
        estadio.getConciertos().add(conciertoExistente);
        testEntityManager.persist(conciertoExistente);
        
        // Creamos el nuevo concierto que intentaremos agregar.
        ConciertoEntity conciertoNuevo = new ConciertoEntity();
        conciertoNuevo.setNombre("Gesaffelstein");
        conciertoNuevo.setAforo(500);
        conciertoNuevo.setPresupuesto(500000L);
        // La fecha de este concierto viola la regla (solo 1 día de diferencia).
        conciertoNuevo.setFecha(LocalDateTime.now().plusDays(31));
        testEntityManager.persist(conciertoNuevo);

        testEntityManager.flush();

        // 2. Act & Assert: Verificamos que se lanza la excepción de regla de negocio.
        assertThrows(IllegalOperationException.class, () -> {
            conciertoEstadioService.crearRelacionConciertoEstadio(conciertoNuevo.getId(), estadio.getId());
        });
    }
}
