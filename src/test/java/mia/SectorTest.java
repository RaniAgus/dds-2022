package mia;

import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static mia.factory.MiembroFactory.agus;
import static mia.factory.SectorFactory.unSectorConSolicitudes;
import static mia.factory.SectorFactory.unSectorVacio;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SectorTest {

  @Test
  public void unMiembroPuedeSolicitarVincularse() {
    Sector sector = unSectorVacio();
    Miembro miembro = agus();

    sector.solicitarVinculacion(miembro);

    assertTrue(sector.getMiembrosPendientes().contains(miembro));
  }

  @Test
  public void alAceptarUnMiembroPasaAEstarVinculadoAlSector() {
    Miembro miembro = agus();
    Sector sector = unSectorConSolicitudes(singletonList(miembro));

    sector.vincularMiembro(miembro);

    assertTrue(sector.getMiembros().contains(miembro));
  }

  @Test
  public void alAceptarUnMiembroDejaDeEstarPendiente() {
    Miembro miembro = agus();
    Sector sector = unSectorConSolicitudes(singletonList(miembro));

    sector.vincularMiembro(miembro);

    assertFalse(sector.getMiembrosPendientes().contains(miembro));
  }

}
