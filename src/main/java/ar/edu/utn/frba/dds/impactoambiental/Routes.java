package ar.edu.utn.frba.dds.impactoambiental;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.get;

import ar.edu.utn.frba.dds.impactoambiental.controllers.AgenteSectorialController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.HomeController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.MiembroController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.OrganizacionController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.UsuarioController;
import java.util.Optional;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {
  public static void main(String[] args) {
    HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();

    HomeController homeController = new HomeController();
    UsuarioController usuarioController = new UsuarioController();
    MiembroController miembroController = new MiembroController();
    OrganizacionController organizacionController = new OrganizacionController();
    AgenteSectorialController agenteSectorialController = new AgenteSectorialController();

    port(getPort());

    get("/", homeController::home);
    get("/recomendaciones", homeController::recomendaciones, templateEngine);

    get("/login", usuarioController::verLogin, templateEngine);
    post("/login", usuarioController::iniciarSesion);
    post("/logout", usuarioController::cerrarSesion, templateEngine);

    path("/miembros/:miembro", () -> {
      before((req, res) -> { /* TODO: Validar usuario miembro */});
      get("/vinculaciones", miembroController::vinculaciones, templateEngine);
      post("/vinculaciones", miembroController::proponerVinculacion, templateEngine);
      path("/vinculaciones/:vinculacion", () -> {
        get("/trayectos", miembroController::trayectos, templateEngine);
        get("/trayectos/nuevo", miembroController::nuevoTrayecto, templateEngine);
        post("/trayectos/nuevo", miembroController::anadirTrayecto, templateEngine);
        get("/trayectos/nuevo/tramos/nuevo", miembroController::nuevoTramo, templateEngine);
        post("/trayectos/nuevo/tramos", miembroController::anadirTramo, templateEngine);
      });
    });

    path("/organizaciones/:id", () -> {
      before((req, res) -> { /* TODO: Validar usuario organizacional */});
      get("/vinculaciones", organizacionController::vinculaciones, templateEngine);
      post("/vinculaciones", organizacionController::aceptarVinculacion, templateEngine);
      get("/da", organizacionController::da, templateEngine);
      post("/da", organizacionController::cargarDA, templateEngine);
      get("/reportes", organizacionController::reportes, templateEngine);
    });

    path("/sectoresterritoriales/:id", () -> {
      before((req, res) -> { /* TODO: Validar usuario territorial */});
      get("/reportes", agenteSectorialController::reportes, templateEngine);
    });

    after("/*", (req, res) -> PerThreadEntityManagers.getEntityManager().clear());
  }

  private static Integer getPort() {
    return Optional.ofNullable(System.getenv("PORT"))
        .map(Integer::parseInt)
        .orElse(8080);
  }
}
