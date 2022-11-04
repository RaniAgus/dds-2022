package ar.edu.utn.frba.dds.impactoambiental;

import static java.util.Arrays.asList;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.UnidadDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
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
      Recomendacion recomendacion = new Recomendacion(LocalDate.now(),"Recomendaciones de Noviembre","Tremendas recomendaciones","/recomendaciones");
      Recomendacion recomendacion2 = new Recomendacion(LocalDate.now(),"Recomendaciones de Octubre","Tremendas recomendaciones","/recomendaciones");
      List<Trayecto> trayectos = new ArrayList<>();
      Parada parada = new Parada("Lo De Marco", new Distancia(10D, Unidad.KM), new Distancia(10D, Unidad.KM));
      Parada parada2 = new Parada("Lo De Uli", new Distancia(10D, Unidad.KM), new Distancia(10D, Unidad.KM));
      Linea linea = new Linea("Linea Functional", asList(parada, parada2), new MedioDeTransporte("Hola", 30D, new TipoDeConsumo("Consumo Faalopa", 30D, UnidadDeConsumo.KM), TipoDeTransporte.TRANSPORTE_PUBLICO));
      Miembro miembro = new Miembro("juan", "perroGato00", "42885123", TipoDeDocumento.DNI, trayectos);
      Miembro miembro2 = new Miembro("uli", "gatoGato77", "43998830", TipoDeDocumento.DNI, trayectos);
      Vinculacion vinculacion = new Vinculacion(miembro);
      Vinculacion vinculacion2 = new Vinculacion(miembro2);
//      vinculacion.aceptar();
      Sector sector = new Sector("Halloween", asList(vinculacion));
      Sector sector2 = new Sector("Navidad", asList(vinculacion2));
      Organizacion organizacion = new Organizacion("PEPE SA", null, TipoDeOrganizacion.EMPRESA,
          ClasificacionDeOrganizacion.EMPRESA_PRIMARIA, asList(sector, sector2), null, null);
      UsuarioMiembro usuarioMiembro = new UsuarioMiembro("juan", "perroGato00", "juan", "juan", "42885123", TipoDeDocumento.DNI, asList(miembro));
      UsuarioMiembro usuarioMiembro2 = new UsuarioMiembro("juan", "gatoGato77", "ulises", "cabaleiro", "43998830", TipoDeDocumento.DNI, asList(miembro2));
      UsuarioOrganizacion usuarioOrganizacion = new UsuarioOrganizacion("pepe", "peperroGato00", organizacion);
      SectorTerritorial sectorTerritorial = new SectorTerritorial("Halloween", asList(organizacion));
      UsuarioSectorTerritorial usuarioSectorTerritorial = new UsuarioSectorTerritorial("halloween", "halloweenGato00", sectorTerritorial);
      persist(miembro);
      persist(vinculacion);
      persist(sector);
      persist(miembro2);
      persist(vinculacion2);
      persist(sector2);
      persist(organizacion);
      persist(usuarioMiembro);
      persist(usuarioMiembro2);
      persist(parada);
      persist(parada2);
      persist(linea);
      persist(usuarioOrganizacion);
      persist(sectorTerritorial);
      persist(usuarioSectorTerritorial);
      persist(recomendacion);
      persist(recomendacion2);
    });
  }
}
