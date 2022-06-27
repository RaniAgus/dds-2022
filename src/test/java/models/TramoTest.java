package models;

import models.mediodetransporte.Linea;
import models.mediodetransporte.Parada;
import models.miembro.Tramo;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TramoTest extends BaseTest {

  @Test
  public void laDistanciaDeUnTramoPublicoEsLaSumaDeLasDistanciasDeSusParadas(){
    Parada paradaInicial = crearParada(30);
    Parada paradaFinal = crearParada(50);
    Linea linea = crearLineaDeSubteConParadas(asList(paradaInicial, crearParada(10), paradaFinal));

    Tramo tramo = crearTramoEnTransportePublico(paradaInicial, paradaFinal, linea);

    assertEquals(crearDistanciaEnKm(40), tramo.getDistancia());
  }

  @Test
  public void laDistanciaDeUnTramoPrivadoSeCalculaPorAPI() {
    when(geolocalizador.medirDistancia(utnMedrano, utnCampus)).thenReturn(crearDistanciaEnKm(50));

    Tramo tramo = crearTramoEnBicicleta(utnMedrano, utnCampus);

    verify(geolocalizador, times(1)).medirDistancia(utnMedrano, utnCampus);
    assertEquals(crearDistanciaEnKm(50), tramo.getDistancia());
  }

}
