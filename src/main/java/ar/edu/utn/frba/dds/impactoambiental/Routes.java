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
import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;

import java.util.Optional;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import spark.Request;
import spark.Response;
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
      before(Routes::validarUsuario);
      after(Routes::eliminarUsuarioDeSesion);

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
      before(Routes::validarUsuario);
      after(Routes::eliminarUsuarioDeSesion);
      
      get("/vinculaciones", organizacionController::vinculaciones, templateEngine);
      post("/vinculaciones", organizacionController::aceptarVinculacion, templateEngine);
      get("/da", organizacionController::da, templateEngine);
      post("/da", organizacionController::cargarDA, templateEngine);
      get("/reportes/individual", organizacionController::reportesIndividual, templateEngine);
      get("/reportes/evolucion", organizacionController::reportesEvolucion, templateEngine);
    });

    path("/sectoresterritoriales/:id", () -> {
      before(Routes::validarUsuario);
      after(Routes::eliminarUsuarioDeSesion);

      get("/reportes/consumo/individual", agenteSectorialController::reportesConsumoIndividual, templateEngine);
      get("/reportes/consumo/evolucion", agenteSectorialController::reportesConsumoEvolucion, templateEngine);
      get("/reportes/organizacion/individual", agenteSectorialController::reportesOrganizacionIndividual, templateEngine);
      get("/reportes/organizacion/evolucion", agenteSectorialController::reportesOrganizacionEvolucion, templateEngine);
    });

    after("/*", (req, res) -> PerThreadEntityManagers.getEntityManager().clear());
  }

  private static Integer getPort() {
    return Optional.ofNullable(System.getenv("PORT"))
        .map(Integer::parseInt)
        .orElse(8080);
  }

  private static void validarUsuario(Request req, Response res) {
    Optional<Usuario> usuario = Optional.ofNullable(req.params("usuario"))
      .map(Long::valueOf)
      .filter(id -> id.equals(req.attribute("usuarioId")))
      .flatMap(id -> RepositorioUsuarios.getInstance().obtenerPorID(id))
      .map(u -> { req.attribute("usuario", u); return u; });
        
    if (!usuario.isPresent()) { // Quizas habia que usar either en vez de optional
      res.redirect("/");
    }
  }

  private static void eliminarUsuarioDeSesion(Request req, Response res) {
    req.session().removeAttribute("usuario");
  }
}
