package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.da.DatoActividad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoPrivado;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.EstadoVinculo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import org.junit.jupiter.api.Assertions;
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

  // TPA3 - Calculos HC de Miembro Mensual
  @Test
  public void elHCMensualDeUnMiembroEsLaSumaDeSusTrayectosPorDiasLaboralesDeUnMes() {
    Trayecto primerTrayecto = mock(Trayecto.class);
    when(primerTrayecto.carbonoEquivalente()).thenReturn(10.0);
    Trayecto segundoTrayecto = mock(Trayecto.class);
    when(segundoTrayecto.carbonoEquivalente()).thenReturn(20.0);
    
    Miembro miembro = crearMiembro();
    miembro.darDeAltaTrayecto(primerTrayecto);
    miembro.darDeAltaTrayecto(segundoTrayecto);

    assertThat(miembro.huellaCarbonoPersonal(Periodicidad.MENSUAL)).isEqualTo(30.0 * 20);
  }

  // TPA3 - Calculos HC de Miembro Anual
  @Test
  public void elHCAnualDeUnMiembroEsLaSumaDeLosTrayectosPorDiasLaboralesDeUnAnio() {
    Trayecto primerTrayecto = mock(Trayecto.class);
    when(primerTrayecto.carbonoEquivalente()).thenReturn(10.0);
    Trayecto segundoTrayecto = mock(Trayecto.class);
    when(segundoTrayecto.carbonoEquivalente()).thenReturn(20.0);
    
    Miembro miembro = crearMiembro();
    miembro.darDeAltaTrayecto(primerTrayecto);
    miembro.darDeAltaTrayecto(segundoTrayecto);

    assertThat(miembro.huellaCarbonoPersonal(Periodicidad.ANUAL)).isEqualTo(30.0 * 240);
  }

  @Test
  public void elHCDeUnSectorEsLaSumatoriaDeTodosLosTrayectosDeSusMiembros() {
    Trayecto primerTrayecto = mock(Trayecto.class);
    when(primerTrayecto.carbonoEquivalente()).thenReturn(10.0);
    Trayecto segundoTrayecto = mock(Trayecto.class);
    when(segundoTrayecto.carbonoEquivalente()).thenReturn(20.0);
    
    Miembro miembro = crearMiembro();
    miembro.darDeAltaTrayecto(primerTrayecto);
    miembro.darDeAltaTrayecto(segundoTrayecto);
    
    Trayecto tercerTrayecto = mock(Trayecto.class);
    when(tercerTrayecto.carbonoEquivalente()).thenReturn(50.0);
    
    Miembro otroMiembro = crearMiembro();
    otroMiembro.darDeAltaTrayecto(tercerTrayecto);
    
    Sector sector = crearSectorVacio();
    sector.solicitarVinculacion(crearVinculacionPendiente(miembro));
    sector.solicitarVinculacion(crearVinculacionPendiente(otroMiembro));
    sector.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE).forEach(Vinculacion::aceptar);
    
    assertThat(sector.huellaCarbono(Periodicidad.ANUAL)).isEqualTo((10.0 + 20.0 + 50.0) * 240);
  }
  
  @Test
  public void sePuedeSaberElHCPromedioDeUnSector() {
    Trayecto primerTrayecto = mock(Trayecto.class);
    when(primerTrayecto.carbonoEquivalente()).thenReturn(10.0);
    Trayecto segundoTrayecto = mock(Trayecto.class);
    when(segundoTrayecto.carbonoEquivalente()).thenReturn(20.0);
    
    Miembro miembro = crearMiembro();
    miembro.darDeAltaTrayecto(primerTrayecto);
    miembro.darDeAltaTrayecto(segundoTrayecto);
    
    Trayecto tercerTrayecto = mock(Trayecto.class);
    when(tercerTrayecto.carbonoEquivalente()).thenReturn(50.0);
    
    Miembro otroMiembro = crearMiembro();
    otroMiembro.darDeAltaTrayecto(tercerTrayecto);
    
    Sector sector = crearSectorVacio();
    sector.solicitarVinculacion(crearVinculacionPendiente(miembro));
    sector.solicitarVinculacion(crearVinculacionPendiente(otroMiembro));
    sector.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE).forEach(Vinculacion::aceptar);
    
    assertThat(sector.huellaCarbonoPorMiembro(Periodicidad.ANUAL)).isEqualTo((10.0 + 20.0 + 50.0) * 240 / 2);
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
    sector.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE).forEach(Vinculacion::aceptar);

    m1.darDeAltaTrayecto(trayectoCompartido);
    m2.darDeAltaTrayecto(trayectoCompartido);

    assertThat(sector.huellaCarbono(Periodicidad.MENSUAL)).isEqualTo(200.0);
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
    
    sector1.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE).forEach(Vinculacion::aceptar);
    sector2.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE).forEach(Vinculacion::aceptar);


    m1.darDeAltaTrayecto(trayectoCompartido);
    m2.darDeAltaTrayecto(trayectoCompartido);

    Organizacion org = crearOrganizacionVacia();
    org.darDeAltaSector(sector1);
    org.darDeAltaSector(sector2);

    assertThat(org.huellaCarbonoTrayectos(Periodicidad.MENSUAL)).isEqualTo(200.0);
  }

  @Test
  public void laHCDeUnDatoActividadEsSuCantidadConsumidaPorElFE(){
    DatoActividad dato = new DatoActividad(nafta, 10.0, Periodicidad.MENSUAL, LocalDate.now());
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
    when(DA1.estaEnPeriodo(any(), any())).thenReturn(true);
    when(DA1.carbonoEquivalente()).thenReturn(10.0);

    DatoActividad DA2 = mock(DatoActividad.class);
    when(DA2.estaEnPeriodo(any(), any())).thenReturn(true);
    when(DA2.carbonoEquivalente()).thenReturn(12.0);

    org.agregarDatosActividad(asList(DA1, DA2));

    Assertions.assertEquals(300.0 + 22.0, org.huellaCarbono(LocalDate.now(), Periodicidad.MENSUAL));
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
    when(DA1.estaEnPeriodo(any(), any())).thenReturn(true);
    when(DA1.carbonoEquivalente()).thenReturn(25.0);

    DatoActividad DA2 = mock(DatoActividad.class);
    when(DA2.estaEnPeriodo(any(), any())).thenReturn(true);
    when(DA2.carbonoEquivalente()).thenReturn(25.0);

    org.agregarDatosActividad(asList(DA1, DA2));

    assertThat(m1.impactoCarbonoEnOrganizacion(org, LocalDate.now(), Periodicidad.MENSUAL))
      .isEqualTo(200.0 / (200.0 + 100.0 + 25.0 + 25.0));
  }

  @Test
  public void laHCDeUnSectorTerritorialEsLaSumaDeLaHCDeSusOrganizaciones() {

    Organizacion org1 = Mockito.mock(Organizacion.class);
    when(org1.huellaCarbono(any(), any())).thenReturn(20.0);

    Organizacion org2 = Mockito.mock(Organizacion.class);
    when(org2.huellaCarbono(any(), any())).thenReturn(7.0);

    SectorTerritorial sectorTerritorial = new SectorTerritorial("sector de prueba", asList(org1, org2));

    Assertions.assertEquals(27, sectorTerritorial.huellaCarbono(LocalDate.now(), Periodicidad.MENSUAL));
  }  

}
