package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.controllers.AgenteSectorialController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.HomeController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.MiembroController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.OrganizacionController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.UsuarioController;
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
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.*;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Request;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Routes {
  public static void main(String[] args) {
    EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
    EntityTransaction transac =entityManager.getTransaction();
    transac.begin();
    List<Trayecto> trayectos = new ArrayList<>();
    Parada parada = new Parada("Lo De Marco",new Distancia(10D, Unidad.KM),new Distancia(10D, Unidad.KM));
    Parada parada2 = new Parada("Lo De Uli",new Distancia(10D, Unidad.KM),new Distancia(10D, Unidad.KM));
    Linea linea = new Linea("Linea Functional",asList(parada,parada2), new MedioDeTransporte("Hola",30D, new TipoDeConsumo("Consumo Faalopa",30D, UnidadDeConsumo.KM), TipoDeTransporte.TRANSPORTE_PUBLICO));
    Miembro miembro = new Miembro("juan","perroGato00","42885123" ,TipoDeDocumento.DNI, trayectos);
    Vinculacion vinculacion = new Vinculacion(miembro);
    Sector sector = new Sector(asList(vinculacion) );
    Organizacion organizacion = new Organizacion("PEPE SA",null, TipoDeOrganizacion.EMPRESA,
        ClasificacionDeOrganizacion.EMPRESA_PRIMARIA,asList(sector),null,null);
    UsuarioMiembro usuarioMiembro= new UsuarioMiembro("juan","perroGato00","juan","perroGato00", "42885123",TipoDeDocumento.DNI,asList(miembro));
    entityManager.persist(miembro);
    entityManager.persist(vinculacion);
    entityManager.persist(sector);
    entityManager.persist(organizacion);
    entityManager.persist(usuarioMiembro);
    entityManager.persist(parada);
    entityManager.persist(parada2);
    entityManager.persist(linea);
    transac.commit();
    HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();

    HomeController homeController = new HomeController();
    UsuarioController usuarioController = new UsuarioController();
    MiembroController miembroController = new MiembroController();
    OrganizacionController organizacionController = new OrganizacionController();
    AgenteSectorialController agenteSectorialController = new AgenteSectorialController();

    Spark.port(8080); // Esto se deberia obtener por variable de entorno/service locator??

    Spark.get("/", homeController::home);
    Spark.get("/recomendaciones", homeController::recomendaciones, templateEngine);

    Spark.get("/login", usuarioController::verLogin, templateEngine);
    Spark.post("/login", usuarioController::iniciarSesion);
    Spark.post("/logout", usuarioController::cerrarSesion, templateEngine);

    Spark.get("/miembros/:miembro/vinculaciones", miembroController::vinculaciones, templateEngine);
    Spark.post("/miembros/:miembro/vinculaciones", miembroController::proponerVinculacion, templateEngine);
    Spark.get("/miembros/:miembro/vinculaciones/:vinculacion/trayectos", miembroController::trayectos, templateEngine);
    Spark.get("/miembros/:miembro/vinculaciones/:vinculacion/trayectos/nuevo", miembroController::nuevoTrayecto, templateEngine);
    Spark.post("/miembros/:miembro/vinculaciones/:vinculacion/trayectos/nuevo", miembroController::anadirTrayecto, templateEngine);
    Spark.get("/miembros/:miembro/vinculaciones/:vinculacion/trayectos/nuevo/tramos/nuevo", miembroController::nuevoTramo, templateEngine); //TODO: Pensar un mejor nombre que tramos()
    Spark.post("/miembros/:miembro/vinculaciones/:vinculacion/trayectos/nuevo/tramos", miembroController::anadirTramo, templateEngine);

    Spark.get("/organizaciones/:id/vinculaciones", organizacionController::vinculaciones, templateEngine);
    Spark.post("/organizaciones/:id/vinculaciones", organizacionController::aceptarVinculacion, templateEngine);
    Spark.get("/organizaciones/:id/da", organizacionController::da, templateEngine);
    Spark.post("/organizaciones/:id/da", organizacionController::cargarDA, templateEngine);
    Spark.get("/organizaciones/:id/reportes", organizacionController::reportes, templateEngine);

    Spark.get("/sectoresterritoriales/:id/reportes", agenteSectorialController::reportes, templateEngine);
   // Spark.after("/*",  (request, response) -> PerThreadEntityManagers.getEntityManager().clear() );
  }
}
