package ar.edu.utn.frba.dds.impactoambiental.models;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;

import ar.edu.utn.frba.dds.impactoambiental.models.da.DatosActividadesParser;
import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.UnidadDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Unidad;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.TipoDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TipoDeDocumento;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoEnTransportePublico;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoPrivado;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.ClasificacionDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import ar.edu.utn.frba.dds.impactoambiental.models.validador.Validador;
import ar.edu.utn.frba.dds.impactoambiental.models.validador.Validar10MilContrasenas;
import ar.edu.utn.frba.dds.impactoambiental.models.validador.Validar8Caracteres;
import ar.edu.utn.frba.dds.impactoambiental.models.validador.ValidarCaracteresConsecutivos;
import ar.edu.utn.frba.dds.impactoambiental.models.validador.ValidarCaracteresRepetidos;
import ar.edu.utn.frba.dds.impactoambiental.models.validador.ValidarUsuarioPorDefecto;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioTipoDeConsumo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public abstract class BaseTest extends AbstractPersistenceTest
    implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
  protected Ubicacion utnMedrano = new Ubicacion(1, "Medrano", "951");
  protected Ubicacion utnCampus = new Ubicacion(1, "Mozart", "2300");

  protected TipoDeConsumo electricidad = new TipoDeConsumo("ELECTRICIDAD", 1.0, UnidadDeConsumo.M3);
  protected TipoDeConsumo nafta = new TipoDeConsumo("NAFTA", 1.0, UnidadDeConsumo.LTS);

  protected MedioDeTransporte bicicleta = new MedioDeTransporte("BICICLETA", 0.0, null, TipoDeTransporte.BICICLETA_O_PIE);
  protected MedioDeTransporte taxi = new MedioDeTransporte("TAXI", 1.0, nafta, TipoDeTransporte.SERVICIO_CONTRATADO);
  protected MedioDeTransporte subte = new MedioDeTransporte("SUBTE", 10.0, nafta, TipoDeTransporte.TRANSPORTE_PUBLICO);
  protected MedioDeTransporte automovil = new MedioDeTransporte("AUTOMOVIL", 1.0, nafta, TipoDeTransporte.VEHICULO_PARTICULAR);

  protected LectorDeArchivos lectorDeArchivos;
  protected Geolocalizador geolocalizador;

  @BeforeEach
  void init() {
    lectorDeArchivos = mock(LectorDeArchivos.class);
    geolocalizador = mock(Geolocalizador.class);
    super.setup();
  }

  @AfterEach
  void teardown() {
    super.tearDown();
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
        new ArrayList<>()
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
    return new Linea("Subte B", paradas, subte);
  }

  protected Parada crearParada(int distanciaAAnterior, int distanciaAProxima) {
    return new Parada("Parada", crearDistanciaEnKm(distanciaAAnterior), crearDistanciaEnKm(distanciaAProxima));
  }

  protected Distancia crearDistanciaEnKm(double kilometros) {
    return new Distancia(kilometros, Unidad.KM);
  }

  // Tramos

  protected Tramo crearTramoEnBicicleta(Ubicacion origen, Ubicacion destino) {
    return new TramoPrivado(geolocalizador, origen, destino, bicicleta);
  }

  protected Tramo crearTramoEnTransportePublico(Parada origen, Parada destino, Linea linea) {
    return new TramoEnTransportePublico(origen, destino, linea);
  }

  protected Tramo crearTramoEnServicioContratado() {
    return new TramoPrivado(geolocalizador, utnMedrano, utnCampus, taxi);
  }

  protected Tramo crearTramoEnVehiculoParticular() {
    return new TramoPrivado(geolocalizador, utnMedrano, utnCampus, automovil);
  }

  // Trayectos

  protected Trayecto crearTrayecto(Ubicacion origen, Ubicacion destino) {
    return crearTrayectoConTramos(singletonList(crearTramoEnBicicleta(origen, destino)));
  }

  protected Trayecto crearTrayectoConTramos(List<Tramo> tramos) {
    return new Trayecto(LocalDate.now(), tramos);
  }

  // Datos de Actividad

  protected DatosActividadesParser crearParserDatosDeActividad() {
    persist(electricidad);
    persist(nafta);
    return new DatosActividadesParser(
        new RepositorioTipoDeConsumo(),
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
