package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.da.DatoActividad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoPrivado;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculoHCTest extends BaseTest {

  private Periodo periodoMensual;
  private Periodo periodoAnual;

  @BeforeEach
  public void setup() {
     periodoMensual = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
     periodoAnual = new Periodo(LocalDate.now(), Periodicidad.ANUAL);
  }
  
  // TPA3 - Calculos HC de Tramo
  @Test
  public void elHCDeUnTramoEsSuDistanciaPorElFactorDelTransporte(){
    when(geolocalizador.medirDistancia(utnMedrano, utnCampus)).thenReturn(crearDistanciaEnKm(50));
    assertThat(crearTramoEnServicioContratado().carbonoEquivalente()).isEqualTo(50.0);
  }
  
  // TPA3 - Calculos HC de Trayecto
  @Test
  public void elHCdeUnTrayectoEsLaSumaDeSusTramos() {
    Tramo primerTramo = mock(Tramo.class);
    when(primerTramo.carbonoEquivalente()).thenReturn(10.0);
    Tramo segundoTramo = mock(Tramo.class);
    when(segundoTramo.carbonoEquivalente()).thenReturn(20.0);
    Tramo tercerTramo = mock(Tramo.class);
    when(tercerTramo.carbonoEquivalente()).thenReturn(30.0);

    Trayecto trayecto = crearTrayectoConTramos(asList(primerTramo, segundoTramo, tercerTramo));

    assertThat(trayecto.carbonoEquivalente()).isEqualTo(60.0);
  }

  // TPA3 - Calculos HC de Miembro Mensual y Anual
  @Test
  public void elHCDeUnMiembroEsLaSumaDeSusTrayectosDuranteElPeriodo() {
    Trayecto primerTrayecto = mock(Trayecto.class);
    when(primerTrayecto.carbonoEquivalente()).thenReturn(10.0);
    when(primerTrayecto.estaEnPeriodo(periodoMensual)).thenReturn(true);

    Trayecto segundoTrayecto = mock(Trayecto.class);
    when(segundoTrayecto.carbonoEquivalente()).thenReturn(20.0);
    when(segundoTrayecto.estaEnPeriodo(periodoMensual)).thenReturn(true);

    Trayecto tercerTrayecto = mock(Trayecto.class);
    when(tercerTrayecto.carbonoEquivalente()).thenReturn(35.0);
    when(tercerTrayecto.estaEnPeriodo(periodoMensual)).thenReturn(false);

    Miembro miembro = crearMiembro();
    miembro.darDeAltaTrayecto(primerTrayecto);
    miembro.darDeAltaTrayecto(segundoTrayecto);
    miembro.darDeAltaTrayecto(tercerTrayecto);

    assertThat(miembro.huellaCarbonoPersonal(periodoMensual)).isEqualTo(30.0);
  }

  @Test
  public void elHCDeUnSectorEsLaSumatoriaDeTodosLosTrayectosDeSusMiembros() {
    Trayecto primerTrayecto = mock(Trayecto.class);
    when(primerTrayecto.carbonoEquivalente()).thenReturn(10.0);
    when(primerTrayecto.estaEnPeriodo(periodoAnual)).thenReturn(true);
    Trayecto segundoTrayecto = mock(Trayecto.class);
    when(segundoTrayecto.carbonoEquivalente()).thenReturn(20.0);
    when(segundoTrayecto.estaEnPeriodo(periodoAnual)).thenReturn(true);
    
    Miembro miembro = crearMiembro();
    miembro.darDeAltaTrayecto(primerTrayecto);
    miembro.darDeAltaTrayecto(segundoTrayecto);
    
    Trayecto tercerTrayecto = mock(Trayecto.class);
    when(tercerTrayecto.carbonoEquivalente()).thenReturn(50.0);
    when(tercerTrayecto.estaEnPeriodo(periodoAnual)).thenReturn(true);
    
    Miembro otroMiembro = crearMiembro();
    otroMiembro.darDeAltaTrayecto(tercerTrayecto);
    
    Sector sector = crearSectorVacio();
    sector.solicitarVinculacion(crearVinculacionPendiente(miembro));
    sector.solicitarVinculacion(crearVinculacionPendiente(otroMiembro));
    sector.getVinculacionesPendientes().forEach(Vinculacion::aceptar);
    
    assertThat(sector.huellaCarbono(periodoAnual)).isEqualTo(10.0 + 20.0 + 50.0);
  }
  
  @Test
  public void sePuedeSaberElHCPromedioDeUnSector() {
    Trayecto primerTrayecto = mock(Trayecto.class);
    when(primerTrayecto.carbonoEquivalente()).thenReturn(10.0);
    when(primerTrayecto.estaEnPeriodo(periodoAnual)).thenReturn(true);
    Trayecto segundoTrayecto = mock(Trayecto.class);
    when(segundoTrayecto.carbonoEquivalente()).thenReturn(20.0);
    when(segundoTrayecto.estaEnPeriodo(periodoAnual)).thenReturn(true);
    
    Miembro miembro = crearMiembro();
    miembro.darDeAltaTrayecto(primerTrayecto);
    miembro.darDeAltaTrayecto(segundoTrayecto);
    
    Trayecto tercerTrayecto = mock(Trayecto.class);
    when(tercerTrayecto.carbonoEquivalente()).thenReturn(50.0);
    when(tercerTrayecto.estaEnPeriodo(periodoAnual)).thenReturn(true);
    
    Miembro otroMiembro = crearMiembro();
    otroMiembro.darDeAltaTrayecto(tercerTrayecto);
    
    Sector sector = crearSectorVacio();
    sector.solicitarVinculacion(crearVinculacionPendiente(miembro));
    sector.solicitarVinculacion(crearVinculacionPendiente(otroMiembro));
    sector.getVinculacionesPendientes().forEach(Vinculacion::aceptar);
    
    assertThat(sector.huellaCarbonoPorMiembro(periodoAnual)).isEqualTo((10.0 + 20.0 + 50.0) / 2);
  }

  // TPA3 - Calculos HC de Sector considerando tramos compartidos
  @Test
  public void laHCDeUnTrayectoCompartidoPorMiembrosSeComputaUnaVezEnSector() {
    Miembro m1 = crearMiembro();
    Miembro m2 = crearMiembro();

    TramoPrivado tramo = Mockito.mock(TramoPrivado.class);
    Mockito.when(tramo.esCompartible()).thenReturn(Boolean.TRUE);
    Mockito.when(tramo.carbonoEquivalente()).thenReturn(10.0);

    Trayecto trayectoCompartido = crearTrayectoConTramos(Collections.singletonList(tramo));

    Sector sector = crearSectorVacio();
    sector.solicitarVinculacion(crearVinculacionPendiente(m1));
    sector.solicitarVinculacion(crearVinculacionPendiente(m2));
    sector.getVinculacionesPendientes().forEach(Vinculacion::aceptar);

    m1.darDeAltaTrayecto(trayectoCompartido);
    m2.darDeAltaTrayecto(trayectoCompartido);

    assertThat(sector.huellaCarbono(periodoMensual)).isEqualTo(10.0);
  }

  // TPA3 - Calculos HC de Organizacion considerando tramos compartidos
  @Test
  public void laHCDeUnTrayectoCompartidoPorMiembrosSeComputaUnaVezEnOrganizacion() {
    Miembro m1 = crearMiembro();
    Miembro m2 = crearMiembro();

    TramoPrivado tramo = Mockito.mock(TramoPrivado.class);
    Mockito.when(tramo.esCompartible()).thenReturn(Boolean.TRUE);
    Mockito.when(tramo.carbonoEquivalente()).thenReturn(10.0);

    Trayecto trayectoCompartido = crearTrayectoConTramos(Collections.singletonList(tramo));

    Sector sector1 = crearSectorVacio();
    sector1.solicitarVinculacion(crearVinculacionPendiente(m1));
    Sector sector2 = crearSectorVacio();
    sector2.solicitarVinculacion(crearVinculacionPendiente(m2));

    sector1.getVinculacionesPendientes().forEach(Vinculacion::aceptar);
    sector2.getVinculacionesPendientes().forEach(Vinculacion::aceptar);

    m1.darDeAltaTrayecto(trayectoCompartido);
    m2.darDeAltaTrayecto(trayectoCompartido);

    Organizacion org = crearOrganizacionVacia();
    org.darDeAltaSector(sector1);
    org.darDeAltaSector(sector2);

    assertThat(org.huellaCarbonoTrayectos(periodoMensual)).isEqualTo(10.0);
  }

  @Test
  public void laHCDeUnDatoActividadEsSuCantidadConsumidaPorElFE(){
    DatoActividad dato = new DatoActividad(nafta, 10.0, periodoMensual);
    assertThat(dato.carbonoEquivalente()).isEqualTo(10.0  * nafta.getFactorEmision());
  }

  @Test
  public void laHCDeUnaOrganizacionEsLaHCDeSusDAsYTrayectos() {
    Miembro m1 = crearMiembro();
    Miembro m2 = crearMiembro();
    Tramo tramo = Mockito.mock(Tramo.class);
    Mockito.when(tramo.carbonoEquivalente()).thenReturn(10.0);
    Tramo tramo2 = Mockito.mock(Tramo.class);
    Mockito.when(tramo2.carbonoEquivalente()).thenReturn(5.0);
    Trayecto trayectoCompartido = crearTrayectoConTramos(Collections.singletonList(tramo));
    Trayecto trayecto2 = crearTrayectoConTramos(Collections.singletonList(tramo2));
    m1.darDeAltaTrayecto(trayectoCompartido);
    m2.darDeAltaTrayecto(trayectoCompartido);
    m2.darDeAltaTrayecto(trayecto2);


    Vinculacion vinculacion = new Vinculacion(m1);
    Vinculacion vinculacion2 = new Vinculacion(m2);
    Sector sector = crearSectorConUnaVinculacion(vinculacion);
    Sector sector2 = crearSectorConUnaVinculacion(vinculacion2);
    vinculacion2.aceptar();
    vinculacion.aceptar();

    Organizacion org = crearOrganizacionVacia();
    org.darDeAltaSector(sector);
    org.darDeAltaSector(sector2);
    
    DatoActividad DA1 = mock(DatoActividad.class);
    when(DA1.estaEnPeriodo(any())).thenReturn(true);
    when(DA1.carbonoEquivalente()).thenReturn(10.0);

    DatoActividad DA2 = mock(DatoActividad.class);
    when(DA2.estaEnPeriodo(any())).thenReturn(true);
    when(DA2.carbonoEquivalente()).thenReturn(12.0);

    org.agregarDatosActividad(asList(DA1, DA2));

    Assertions.assertEquals(15.0 + 22.0, org.huellaCarbono(periodoMensual));
  }

  @Test
  public void sePuedeSaberElPorcentajeDeHCDeUnMiembroSobreSuOrganizacion() {
    Miembro m1 = crearMiembro();
    Miembro m2 = crearMiembro();
    Tramo tramo = Mockito.mock(Tramo.class);
    Mockito.when(tramo.carbonoEquivalente()).thenReturn(10.0);
    Tramo tramo2 = Mockito.mock(Tramo.class);
    Mockito.when(tramo2.carbonoEquivalente()).thenReturn(5.0);
    Trayecto trayecto1 = crearTrayectoConTramos(Collections.singletonList(tramo));
    Trayecto trayecto2 = crearTrayectoConTramos(Collections.singletonList(tramo2));
    m1.darDeAltaTrayecto(trayecto1);
    m2.darDeAltaTrayecto(trayecto2);

    Vinculacion vinculacion = new Vinculacion(m1);
    Vinculacion vinculacion2 = new Vinculacion(m2);
    Sector sector = crearSectorConUnaVinculacion(vinculacion);
    Sector sector2 = crearSectorConUnaVinculacion(vinculacion2);
    vinculacion2.aceptar();
    vinculacion.aceptar();

    Organizacion org = crearOrganizacionVacia();
    org.darDeAltaSector(sector);
    org.darDeAltaSector(sector2);
    
    DatoActividad DA1 = mock(DatoActividad.class);
    when(DA1.estaEnPeriodo(any())).thenReturn(true);
    when(DA1.carbonoEquivalente()).thenReturn(25.0);

    DatoActividad DA2 = mock(DatoActividad.class);
    when(DA2.estaEnPeriodo(any())).thenReturn(true);
    when(DA2.carbonoEquivalente()).thenReturn(25.0);

    org.agregarDatosActividad(asList(DA1, DA2));

    assertThat(m1.impactoCarbonoEnOrganizacion(org, periodoMensual))
      .isEqualTo(10.0 / (10.0 + 5.0 + 25.0 + 25.0));
  }

  @Test
  public void laHCDeUnSectorTerritorialEsLaSumaDeLaHCDeSusOrganizaciones() {

    Organizacion org1 = Mockito.mock(Organizacion.class);
    when(org1.huellaCarbono(any())).thenReturn(20.0);

    Organizacion org2 = Mockito.mock(Organizacion.class);
    when(org2.huellaCarbono(any())).thenReturn(7.0);

    SectorTerritorial sectorTerritorial = new SectorTerritorial("sector de prueba", asList(org1, org2));

    Assertions.assertEquals(27, sectorTerritorial.huellaCarbono(periodoMensual));
  }  

}
