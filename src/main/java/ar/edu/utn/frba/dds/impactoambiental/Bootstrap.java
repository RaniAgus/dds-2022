package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.UnidadDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Unidad;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.TipoDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TipoDeDocumento;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.Recomendacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.ClasificacionDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioSectorTerritorial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class Bootstrap implements TransactionalOps, EntityManagerOps, WithGlobalEntityManager {

  public static void main(String[] args) {
    new Bootstrap().init();
  }

  public void init() {
    withTransaction(() -> {
      persistirRecomendaciones();
      persistirLinea(persistirParadas());
      persistirTransportesPrivados();
      List<Vinculacion> juan = persistirMiembroYVinculacion("juan", "perroGato00", new ArrayList<>());
      List<Vinculacion> uli = persistirMiembroYVinculacion("uli", "gatoGato77", new ArrayList<>());
      List<Vinculacion> agus = persistirMiembroYVinculacion("agus", "a", new ArrayList<>());
      Sector halloween = persistirSector("Halloween", juan.get(0), uli.get(1), agus.get(2));
      Sector navidad = persistirSector("Navidad", juan.get(1), uli.get(2), agus.get(0));
      Sector pascuas = persistirSector("Pascuas", juan.get(2), uli.get(0), agus.get(1));
      Organizacion pepeSa = persistirOrganizacion("pepe", "peperroGato00", "PEPE SA", Collections.singletonList(halloween), TipoDeOrganizacion.GUBERNAMENTAL);
      Organizacion juanSa = persistirOrganizacion("ulidesign", "ulidesign", "ULI DISENIOS", Collections.singletonList(pascuas), TipoDeOrganizacion.EMPRESA);
      Organizacion utn = persistirOrganizacion("utn", "utn", "UTN", Collections.singletonList(navidad), TipoDeOrganizacion.GUBERNAMENTAL);
      persistirSectorTerritorial(Arrays.asList(pepeSa, juanSa, utn));
    });
  }

  private void persistirRecomendaciones() {
    List<Recomendacion> recomendaciones = Arrays.asList(
        new Recomendacion(LocalDate.now(),"Recomendaciones de Noviembre","Tremendas recomendaciones","/recomendaciones"),
        new Recomendacion(LocalDate.now(),"Recomendaciones de Octubre","Tremendas recomendaciones","/recomendaciones")
    );
    recomendaciones.forEach(this::persist);
  }

  private List<Parada> persistirParadas() {
    List<Parada> paradas = Arrays.asList(
        new Parada("Parada 1", new Distancia(10D, Unidad.KM), new Distancia(10D, Unidad.KM)),
        new Parada("Parada 2", new Distancia(10D, Unidad.KM), new Distancia(10D, Unidad.KM))
    );
    paradas.forEach(this::persist);
    return paradas;
  }

  private Linea persistirLinea(List<Parada> paradas) {
    Linea linea = new Linea("Linea Functional", paradas, new MedioDeTransporte("Hola", 30D, new TipoDeConsumo("Consumo Faalopa", 30D, UnidadDeConsumo.KM), TipoDeTransporte.TRANSPORTE_PUBLICO));
    persist(linea);
    return linea;
  }

  private List<Vinculacion> persistirMiembroYVinculacion(String usuario, String contrasenia, List<Trayecto> trayectos) {
    Miembro miembro1 = new Miembro(usuario, usuario, "44444444", TipoDeDocumento.DNI, trayectos);
    persist(miembro1);
    Vinculacion vinculacion1 = new Vinculacion(miembro1);
    vinculacion1.aceptar();
    persist(vinculacion1);

    Miembro miembro2 = new Miembro(usuario, usuario, "44444444", TipoDeDocumento.DNI, new ArrayList<>());
    persist(miembro2);
    Vinculacion vinculacion2 = new Vinculacion(miembro2);
    vinculacion2.aceptar();
    persist(vinculacion2);

    Miembro miembro3 = new Miembro(usuario, usuario, "44444444", TipoDeDocumento.DNI, new ArrayList<>());
    persist(miembro3);
    Vinculacion vinculacion3 = new Vinculacion(miembro3);
    persist(vinculacion3);

    UsuarioMiembro usuarioMiembro = new UsuarioMiembro(usuario, contrasenia, usuario, usuario, "12345678", TipoDeDocumento.DNI, new ArrayList<>(Arrays.asList(miembro1, miembro2, miembro3)));
    persist(usuarioMiembro);

    return new ArrayList<>(Arrays.asList(vinculacion1, vinculacion2, vinculacion3));
  }

  private Sector persistirSector(String nombre, Vinculacion v1, Vinculacion v2, Vinculacion v3) {
    Sector sector = new Sector(nombre, new ArrayList<>(Arrays.asList(v1, v2, v3)));
    persist(sector);
    return sector;
  }

  private Organizacion persistirOrganizacion(String username, String password, String nombre, List<Sector> sectores, TipoDeOrganizacion tipo) {
    Organizacion organizacion = new Organizacion(nombre, new Ubicacion(1, "Av. Siempre Viva", "578"), tipo, ClasificacionDeOrganizacion.EMPRESA_PRIMARIA, sectores, null, null);
    persist(organizacion);
    UsuarioOrganizacion usuarioOrganizacion = new UsuarioOrganizacion(username, password, organizacion);
    persist(usuarioOrganizacion);
    return organizacion;
  }

  private void persistirSectorTerritorial(List<Organizacion> organizaciones) {
    SectorTerritorial sectorTerritorial = new SectorTerritorial("Demencia", organizaciones);
    persist(sectorTerritorial);
    UsuarioSectorTerritorial usuarioSectorTerritorial = new UsuarioSectorTerritorial("demencia", "demencia", sectorTerritorial);
    persist(usuarioSectorTerritorial);
  }

  private void persistirTransportesPrivados() {
    List<MedioDeTransporte> transportesPrivados = Arrays.asList(
        new MedioDeTransporte("Auto", 30D, new TipoDeConsumo("Consumo Faalopa", 30D, UnidadDeConsumo.KM), TipoDeTransporte.SERVICIO_CONTRATADO),
        new MedioDeTransporte("Moto", 30D, new TipoDeConsumo("Consumo Faalopa", 30D, UnidadDeConsumo.KM), TipoDeTransporte.SERVICIO_CONTRATADO),
        new MedioDeTransporte("Moto Moto", 30D, new TipoDeConsumo("Consumo Faalopa", 30D, UnidadDeConsumo.KM), TipoDeTransporte.SERVICIO_CONTRATADO)
    );
    transportesPrivados.forEach(this::persist);
  }
}
