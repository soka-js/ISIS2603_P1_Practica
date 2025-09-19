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
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import net.bytebuddy.asm.Advice.OffsetMapping.Factory.Illegal;

@DataJpaTest
@Transactional
@Import(ConciertoService.class)
public class ConciertoServiceTest {

  @Autowired
  private ConciertoService conciertoService;

  @Autowired
  private TestEntityManager testEntityManager;

  @BeforeEach
  void setUp(){
    testEntityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
  }

  @Test
  void creacionExitosaConcierto() throws IllegalOperationException{
    ConciertoEntity newConcierto = new ConciertoEntity();
    newConcierto.setNombre("Word tour");
    newConcierto.setNombreArtista("Pink floyd");
    newConcierto.setAforo(35000);
    newConcierto.setPresupuesto(120000L);
    newConcierto.setFecha(LocalDateTime.now().plusDays(30));

    ConciertoEntity crearConcierto = conciertoService.creacionConcierto(newConcierto);
    assertNotNull(crearConcierto);

    ConciertoEntity pruebaBD = testEntityManager.find(ConciertoEntity.class, crearConcierto.getId());
    assertNotNull(pruebaBD);

    assertEquals(newConcierto.getAforo(), pruebaBD.getAforo());
    assertEquals(newConcierto.getPresupuesto(), pruebaBD.getPresupuesto());
    assertEquals(newConcierto.getFecha(), pruebaBD.getFecha());
    assertEquals(newConcierto.getNombreArtista(), pruebaBD.getNombreArtista());
  }

  @Test
  void falloDecreacion() throws IllegalOperationException{
    ConciertoEntity conciertoInvalido = new ConciertoEntity();
    conciertoInvalido.setNombre("Word tour");
    conciertoInvalido.setNombreArtista("Pink floyd");
    conciertoInvalido.setAforo(5);
    conciertoInvalido.setPresupuesto(120000L);
    conciertoInvalido.setFecha(LocalDateTime.now().plusDays(30));

    assertThrows(IllegalOperationException.class, () -> {
      conciertoService.creacionConcierto(conciertoInvalido);
    });

  }
}
