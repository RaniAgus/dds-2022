package models;

import models.geolocalizacion.Unidad;
import models.mediodetransporte.Linea;
import models.mediodetransporte.Parada;
import models.miembro.Tramo;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TramoTest extends BaseTest {

  @Test
  public void laDistanciaDeUnTramoPublicoEsLaSumaDeLasDistanciasDeSusParadasALaIda(){
    Parada paradaInicial = crearParada(0, 30);
    Parada paradaFinal = crearParada(0, 50);
    Linea linea = crearLineaDeSubteConParadas(asList(paradaInicial, crearParada(0, 10), paradaFinal));

    Tramo tramo = crearTramoEnTransportePublico(paradaInicial, paradaFinal, linea);

    assertThat(tramo.getDistancia())
        .extracting("valor", "unidad")
        .containsExactly(40, Unidad.KM);
  }

  @Test
  public void laDistanciaDeUnTramoPublicoEsLaSumaDeLasDistanciasDeSusParadasALaVuelta(){
    Parada paradaInicial = crearParada(30, 0);
    Parada paradaFinal = crearParada(50, 0);
    Linea linea = crearLineaDeSubteConParadas(asList(paradaFinal, crearParada(10, 100), paradaInicial));

    Tramo tramo = crearTramoEnTransportePublico(paradaInicial, paradaFinal, linea);

    assertThat(tramo.getDistancia())
        .extracting("valor", "unidad")
        .containsExactly(60, Unidad.KM);
  }

  @Test
  public void laDistanciaDeUnTramoPrivadoSeCalculaPorAPI() {
    when(geolocalizador.medirDistancia(utnMedrano, utnCampus)).thenReturn(crearDistanciaEnKm(50));

    Tramo tramo = crearTramoEnBicicleta(utnMedrano, utnCampus);

    verify(geolocalizador, times(1)).medirDistancia(utnMedrano, utnCampus);
    assertThat(tramo.getDistancia())
        .extracting("valor", "unidad")
        .containsExactly(50, Unidad.KM);
  }

}
