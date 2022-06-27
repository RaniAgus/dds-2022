package models;

import models.da.DatosActividadesParser;
import models.da.LectorDeArchivos;
import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;
import models.geolocalizacion.Ubicacion;
import models.geolocalizacion.Unidad;
import models.mediodetransporte.BicicletaOPie;
import models.mediodetransporte.Linea;
import models.mediodetransporte.Parada;
import models.mediodetransporte.TipoDeTransportePublico;
import models.miembro.*;
import models.organizacion.*;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;

public abstract class BaseTest {
  protected LectorDeArchivos lectorDeArchivos;
  protected Geolocalizador geolocalizador;
  protected Ubicacion utnMedrano = new Ubicacion(1, "Medrano", "951");
  protected Ubicacion utnCampus = new Ubicacion(1, "Mozart", "2300");

  @BeforeEach
  void init() {
    lectorDeArchivos = mock(LectorDeArchivos.class);
    geolocalizador = mock(Geolocalizador.class);
  }

  // Organizaciones

  protected Organizacion crearOrganizacionVacia() {
    return new Organizacion(
        "UTN",
        utnMedrano,
        TipoDeOrganizacion.INSTITUCION,
        ClasificacionDeOrganizacion.UNIVERSIDAD,
        emptyList(),
        emptyList()
    );
  }

  // Sectores

  public Sector crearSectorVacio() {
    return new Sector(emptyList());
  }

  public Sector crearSectorConUnaVinculacion(Vinculacion vinculacion) {
    return new Sector(singletonList(vinculacion));
  }

  // Miembros

  protected Vinculacion crearVinculacionPendiente(Miembro miembro) {
    return new Vinculacion(miembro);
  }

  protected Miembro crearMiembro() {
    return new Miembro("John", "Doe", "0", TipoDeDocumento.DNI, crearTrayectoConTramos(emptyList()));
  }

  // Lineas

  protected Linea crearLineaDeSubteVacia() {
    return crearLineaDeSubteConParadas(Collections.emptyList());
  }

  protected Linea crearLineaDeSubteConParadas(List<Parada> paradas) {
    return new Linea("Subte B", paradas, TipoDeTransportePublico.SUBTE);
  }

  protected Parada crearParada(double distanciaAProxima) {
    return new Parada(crearDistanciaEnKm(distanciaAProxima), "Parada");
  }

  protected Distancia crearDistanciaEnKm(double kilometros) {
    return new Distancia(BigDecimal.valueOf(kilometros), Unidad.KM);
  }

  // Tramos

  protected Tramo crearTramoEnBicicleta(Ubicacion origen, Ubicacion destino) {
    return new TramoPrivado(geolocalizador, origen, destino, new BicicletaOPie());
  }

  protected Tramo crearTramoEnTransportePublico(Parada origen, Parada destino, Linea linea) {
    return new TramoEnTransportePublico(origen, destino, linea);
  }

  // Trayectos

  protected Trayecto crearTrayectoConTramos(List<Tramo> tramos) {
    return new Trayecto(tramos);
  }

  // Datos de Actividad

  protected DatosActividadesParser crearParserDatosDeActividad() {
    return new DatosActividadesParser(lectorDeArchivos, 1, ';');
  }
}
