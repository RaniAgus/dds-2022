package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.da.*;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Unidad;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.*;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.*;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.*;
import ar.edu.utn.frba.dds.impactoambiental.models.validador.*;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;

public abstract class BaseTest {
  protected Ubicacion utnMedrano = new Ubicacion(1, "Medrano", "951");
  protected Ubicacion utnCampus = new Ubicacion(1, "Mozart", "2300");

  protected TipoDeConsumo electricidad = new TipoDeConsumo("ELECTRICIDAD", 1.0, UnidadDeConsumo.M3);
  protected TipoDeConsumo nafta = new TipoDeConsumo("NAFTA", 1.0, UnidadDeConsumo.LTS);

  protected LectorDeArchivos lectorDeArchivos;
  protected Geolocalizador geolocalizador;

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
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<Contacto>()
    );
  }

  // Sectores

  public Sector crearSectorVacio() {
    return new Sector(new ArrayList<>());
  }

  public Sector crearSectorConUnaVinculacion(Vinculacion vinculacion) {
    return new Sector(singletonList(vinculacion));
  }

  // Miembros

  protected Vinculacion crearVinculacionPendiente(Miembro miembro) {
    return new Vinculacion(miembro);
  }

  protected Miembro crearMiembro() {
    return new Miembro("John", "Doe", "0", TipoDeDocumento.DNI, new ArrayList<>());
  }

  // Lineas

  protected Linea crearLineaDeSubteVacia() {
    return crearLineaDeSubteConParadas(new ArrayList<>());
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
        new ServicioContratado(new TipoDeServicioContratado("Taxi", nafta, 1.0))
    );
  }

  protected Tramo crearTramoEnVehiculoParticular() {
    return new TramoPrivado(
        geolocalizador,
        utnMedrano,
        utnCampus,
        new VehiculoParticular(TipoDeVehiculoParticular.AUTOMOVIL, nafta)
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

  protected DatosActividadesParser crearParserDatosDeActividad() {
    return new DatosActividadesParser(
        new RepositorioTipoDeConsumo(asList(electricidad, nafta)),
        lectorDeArchivos, 1, ';'
    );
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
