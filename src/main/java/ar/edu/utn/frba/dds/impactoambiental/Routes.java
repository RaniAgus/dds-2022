package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.controllers.AgenteSectorialController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.HomeController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.MiembroController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.OrganizacionController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.UsuarioController;
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

    Spark.port(8080); // Esto se deberia obtener por variable de entorno/service locator??

    Spark.get("/", homeController::home, templateEngine);
    Spark.get("/recomendaciones", homeController::recomendaciones, templateEngine);

    Spark.get("/login", usuarioController::verLogin, templateEngine);
    Spark.post("/login", usuarioController::iniciarSesion, templateEngine);
    Spark.post("/logout", usuarioController::cerrarSesion, templateEngine);

    Spark.get("miembros/:id/vinculaciones", miembroController::vinculaciones, templateEngine);
    Spark.post("miembros/:id/vinculaciones", miembroController::proponerVinculacion, templateEngine); //TODO: Pensar un mejor nombre que proponerVinculacion()
    Spark.get("/miembros/:id/vinculaciones/:id/trayectos", miembroController::trayectos, templateEngine);
    Spark.get("/miembros/:id/vinculaciones/:id/trayectos/nuevo", miembroController::nuevoTrayecto, templateEngine);
    Spark.get("/miembros/:id/vinculaciones/:id/trayectos/nuevo/tramos/nuevo", miembroController::tramos, templateEngine); //TODO: Pensar un mejor nombre que tramos()
    Spark.post("/miembros/:id/vinculaciones/:id/trayectos/nuevo/tramos", miembroController::anadirTramo, templateEngine);

    Spark.get("organizaciones/:id/vinculaciones", organizacionController::vinculaciones, templateEngine);
    Spark.post("/organizaciones/:id/vinculaciones", organizacionController::aceptarVinculacion, templateEngine);
    Spark.get("/organizaciones/:id/da", organizacionController::da, templateEngine);
    Spark.post("/organizaciones/:id/da", organizacionController::cargarDA, templateEngine);
    Spark.get("/organizaciones/:id/reportes", organizacionController::reportes, templateEngine);

    Spark.get("/sectoresterritoriales/:id/reportes", agenteSectorialController::reportes, templateEngine);
  }
}
