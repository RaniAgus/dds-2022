package models;

import models.da.DatosActividadesParser;
import models.da.LectorDeArchivos;
import models.da.RepositorioTipoDeConsumo;
import models.da.TipoDeConsumo;
import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;
import models.geolocalizacion.Ubicacion;
import models.geolocalizacion.Unidad;
import models.mediodetransporte.*;
import models.miembro.*;
import models.organizacion.*;
import models.validador.*;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
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
    return new Miembro("John", "Doe", "0", TipoDeDocumento.DNI, emptyList());
  }

  // Lineas

  protected Linea crearLineaDeSubteVacia() {
    return crearLineaDeSubteConParadas(Collections.emptyList());
  }

  protected Linea crearLineaDeSubteConParadas(List<Parada> paradas) {
    return new Linea("Subte B", paradas, TipoDeTransportePublico.SUBTE);
  }

  protected Parada crearParada(double distanciaAAnterior, double distanciaAProxima) {
    return new Parada("Parada", crearDistanciaEnKm(distanciaAAnterior), crearDistanciaEnKm(distanciaAProxima));
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

  protected Tramo crearTramoEnServicioContratado() {
    return new TramoPrivado(
        geolocalizador,
        utnMedrano,
        utnCampus,
        new ServicioContratado(TipoDeServicioContratado.TAXI)
    );
  }

  protected Tramo crearTramoEnVehiculoParticular() {
    return new TramoPrivado(
        geolocalizador,
        utnMedrano,
        utnCampus,
        new VehiculoParticular(TipoDeVehiculoParticular.AUTOMOVIL, TipoDeCombustible.ELECTRICO)
    );
  }

  // Trayectos

  protected Trayecto crearTrayecto(Ubicacion origen, Ubicacion destino) {
    return crearTrayectoConTramos(singletonList(crearTramoEnBicicleta(origen, destino)));
  }

  protected Trayecto crearTrayectoConTramos(List<Tramo> tramos) {
    return new Trayecto(tramos);
  }

  // Datos de Actividad

  protected DatosActividadesParser crearParserDatosDeActividad(List<TipoDeConsumo> tiposDeConsumo) {
    return new DatosActividadesParser(new RepositorioTipoDeConsumo(tiposDeConsumo), lectorDeArchivos, 1, ';');
  }

  // Validadores

  protected Validador crearValidadorConTodasLasValidaciones() {
    return new Validador(asList(
        new Validar8Caracteres(),
        new ValidarCaracteresRepetidos(),
        new Validar10MilContrasenas(lectorDeArchivos),
        new ValidarCaracteresConsecutivos(),
        new ValidarUsuarioPorDefecto()
    ));
  }

  // Administradores

  protected Administrador crearAdministrador(String contrasenia) {
    return new Administrador(crearValidadorConTodasLasValidaciones(), "Juancito", contrasenia);
  }
}
