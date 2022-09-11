package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MiembroTest extends BaseTest {

  // [TPA2]: Se debe permitir la carga de Trayectos compartidos entre miembros de una organización.

  @Test
  public void sePuedenCompartirTrayectosEntreMiembros() {
    Miembro agus = crearMiembro();
    Miembro juan = crearMiembro();
    Trayecto trayecto = crearTrayecto(utnMedrano, utnCampus);

    agus.darDeAltaTrayecto(trayecto);
    juan.darDeAltaTrayecto(trayecto);

    assertThat(agus.getTrayectos()).contains(trayecto);
    assertThat(juan.getTrayectos()).contains(trayecto);
  }
}
