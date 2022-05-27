package models;
import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;
import models.geolocalizacion.Ubicacion;
import models.geolocalizacion.Unidad;
import models.mediodetransporte.BicicletaOPie;
import models.mediodetransporte.Linea;
import models.mediodetransporte.Parada;
import models.mediodetransporte.TipoDeTransportePublico;
import models.miembro.Tramo;
import models.miembro.TramoPrivado;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static models.factory.UbicacionFactory.alem;
import static models.factory.UbicacionFactory.medrano;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TramoTest {

  @Test
  public void laDistanciaDeUnTramoPublicoEsLaSumaDeLasDistanciasDeSusParadas(){
    Parada paradaInicial= mock(Parada.class);
    Parada paradaIntermedia= mock(Parada.class);
    Parada paradaFinal = mock(Parada.class);
    when(paradaFinal.getDistanciaAProximaParada()).thenReturn(new Distancia(new BigDecimal(50), Unidad.KM));
    when(paradaInicial.getDistanciaAProximaParada()).thenReturn(new Distancia(new BigDecimal(50), Unidad.KM));
    when(paradaIntermedia.getDistanciaAProximaParada()).thenReturn(new Distancia(new BigDecimal(50), Unidad.KM));
    Linea subteC=new Linea("subteC",asList(paradaInicial,paradaIntermedia,paradaFinal), TipoDeTransportePublico.SUBTE);
    assertEquals(100,subteC.distanciaEntreParadas(paradaInicial,paradaFinal).getValor());
  }

  @Test
  public void laDistanciaDeUnTramoPrivadoSeCalculaPorAPI() {
    Geolocalizador geolocalizador=mock(Geolocalizador.class);
    when(geolocalizador.medirDistancia(any(),any())).thenReturn(new Distancia(new BigDecimal(50), Unidad.KM));
    Tramo distanciaDelTramo=new TramoPrivado(geolocalizador,medrano(),alem(),new BicicletaOPie());
    verify(geolocalizador).medirDistancia(any(),any());
    assertEquals(50,distanciaDelTramo.getDistancia().getValor());
  }

}
