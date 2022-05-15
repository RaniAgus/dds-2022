package models;

import models.miembro.Miembro;
import models.organizacion.EstadoVinculo;
import models.organizacion.Sector;
import models.organizacion.Vinculacion;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static models.factory.MiembroFactory.agus;
import static models.factory.SectorFactory.unSectorConSolicitudes;
import static models.factory.SectorFactory.unSectorVacio;
import static org.assertj.core.api.Assertions.assertThat;

public class SectorTest {

  @Test
  public void unMiembroPuedeSolicitarVincularse() {
    Sector sector = unSectorVacio();
    Vinculacion vinculacion = new Vinculacion(agus());

    sector.solicitarVinculacion(vinculacion);

    assertThat(sector.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE)).containsExactly(vinculacion);
  }
  @Test
  public void alAceptarUnMiembroPasaAEstarVinculadoAlSector() {
    Miembro miembro = agus();
    Vinculacion vinculacion = new Vinculacion(miembro);
    Sector sector = unSectorConSolicitudes(singletonList(vinculacion));

    vinculacion.aceptar();

    assertThat(sector.getVinculacionesSegunEstado(EstadoVinculo.ACEPTADO)).containsExactly(vinculacion);
  }
  @Test
  public void alAceptarUnMiembroDejaDeEstarPendiente() {
    Miembro miembro = agus();
    Vinculacion vinculacion = new Vinculacion(miembro);
    Sector sector = unSectorConSolicitudes(singletonList(vinculacion));

    vinculacion.aceptar();

    assertThat(sector.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE)).doesNotContain(vinculacion);
  }
}
