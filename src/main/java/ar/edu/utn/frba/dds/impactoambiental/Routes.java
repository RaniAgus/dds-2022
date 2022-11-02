package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.controllers.AgenteSectorialController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.HomeController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.MiembroController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.OrganizacionController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.UsuarioController;
import java.util.Optional;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {
  public static void main(String[] args) {
    HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();

    HomeController homeController = new HomeController();
    UsuarioController usuarioController = new UsuarioController();
    MiembroController miembroController = new MiembroController();
    OrganizacionController organizacionController = new OrganizacionController();
    AgenteSectorialController agenteSectorialController = new AgenteSectorialController();

    Spark.port(getPort());

    Spark.get("/", homeController::home);
    Spark.get("/recomendaciones", homeController::recomendaciones, templateEngine);

    Spark.get("/login", usuarioController::verLogin, templateEngine);
    Spark.post("/login", usuarioController::iniciarSesion);
    Spark.post("/logout", usuarioController::cerrarSesion, templateEngine);

    Spark.path("/miembros/:miembro", () -> {
      Spark.before((req, res) -> { /* TODO: Validar usuario miembro */});
      Spark.get("/vinculaciones", miembroController::vinculaciones, templateEngine);
      Spark.post("/vinculaciones", miembroController::proponerVinculacion, templateEngine);
      Spark.path("/vinculaciones/:vinculacion", () -> {
        Spark.get("/trayectos", miembroController::trayectos, templateEngine);
        Spark.get("/trayectos/nuevo", miembroController::nuevoTrayecto, templateEngine);
        Spark.post("/trayectos/nuevo", miembroController::anadirTrayecto, templateEngine);
        Spark.get("/trayectos/nuevo/tramos/nuevo", miembroController::nuevoTramo, templateEngine);
        Spark.post("/trayectos/nuevo/tramos", miembroController::anadirTramo, templateEngine);
      });
    });

    Spark.path("/organizaciones/:id", () -> {
      Spark.before((req, res) -> { /* TODO: Validar usuario organizacional */});
      Spark.get("/vinculaciones", organizacionController::vinculaciones, templateEngine);
      Spark.post("/vinculaciones", organizacionController::aceptarVinculacion, templateEngine);
      Spark.get("/da", organizacionController::da, templateEngine);
      Spark.post("/da", organizacionController::cargarDA, templateEngine);
      Spark.get("/reportes", organizacionController::reportes, templateEngine);
    });

    Spark.path("/sectoresterritoriales/:id", () -> {
      Spark.before((req, res) -> { /* TODO: Validar usuario territorial */});
      Spark.get("/reportes", agenteSectorialController::reportes, templateEngine);
    });

   // Spark.after("/*",  (request, response) -> PerThreadEntityManagers.getEntityManager().clear() );
  }

  private static Integer getPort() {
    return Optional.ofNullable(System.getenv("PORT"))
        .map(Integer::parseInt)
        .orElse(8080);
  }
}
