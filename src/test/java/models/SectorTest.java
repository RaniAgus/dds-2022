package models;

import models.miembro.Miembro;
import models.organizacion.EstadoVinculo;
import models.organizacion.Sector;
import models.organizacion.Vinculacion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SectorTest extends BaseTest {

  // [TPA1]: Se debe permitir que un Miembro se vincule con un sector de la organización.
  // Las Organizaciones deben aceptar esta vinculación para que las mediciones brindadas por dicho miembro
  // (como los trayectos) tengan impacto en la Organización.

  @Test
  public void unMiembroPuedeSolicitarVincularseAUnSector() {
    Miembro miembro = crearMiembro();
    Sector sector = crearSectorVacio();

    Vinculacion vinculacion = crearVinculacionPendiente(miembro);
    sector.solicitarVinculacion(vinculacion);

    assertThat(sector.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE)).containsExactly(vinculacion);
  }

  @Test
  public void alAceptarUnMiembroPasaAEstarVinculadoAlSector() {
    Miembro miembro = crearMiembro();
    Vinculacion vinculacion = crearVinculacionPendiente(miembro);
    Sector sector = crearSectorConUnaVinculacion(vinculacion);

    vinculacion.aceptar();

    assertThat(sector.getVinculacionesSegunEstado(EstadoVinculo.ACEPTADO)).containsExactly(vinculacion);
  }

  @Test
  public void alAceptarUnMiembroDejaDeEstarPendiente() {
    Miembro miembro = crearMiembro();
    Vinculacion vinculacion = crearVinculacionPendiente(miembro);
    Sector sector = crearSectorConUnaVinculacion(vinculacion);

    vinculacion.aceptar();

    assertThat(sector.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE)).doesNotContain(vinculacion);
  }
}
