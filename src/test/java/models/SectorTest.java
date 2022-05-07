package models;

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
    Miembro miembro = agus();

    sector.solicitarVinculacion(miembro);

    assertThat(sector.getMiembrosPendientes()).containsExactly(miembro);
  }

  @Test
  public void alAceptarUnMiembroPasaAEstarVinculadoAlSector() {
    Miembro miembro = agus();
    Sector sector = unSectorConSolicitudes(singletonList(miembro));

    sector.vincularMiembro(miembro);

    assertThat(sector.getMiembros()).containsExactly(miembro);
  }

  @Test
  public void alAceptarUnMiembroDejaDeEstarPendiente() {
    Miembro miembro = agus();
    Sector sector = unSectorConSolicitudes(singletonList(miembro));

    sector.vincularMiembro(miembro);

    assertThat(sector.getMiembrosPendientes()).doesNotContain(miembro);
  }

}
