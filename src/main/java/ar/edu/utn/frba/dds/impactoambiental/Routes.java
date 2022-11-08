package ar.edu.utn.frba.dds.impactoambiental;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.notFound;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import ar.edu.utn.frba.dds.impactoambiental.controllers.AgenteSectorialController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.HomeController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.MiembroController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.OrganizacionController;
import ar.edu.utn.frba.dds.impactoambiental.controllers.UsuarioController;
import ar.edu.utn.frba.dds.impactoambiental.exceptions.HttpNotFoundException;
import ar.edu.utn.frba.dds.impactoambiental.exceptions.ValidacionException;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {
  public static void main(String[] args) {
    //new Bootstrap().init();
    HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();
    staticFiles.externalLocation("public");

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
    get("/logout", usuarioController::cerrarSesion, templateEngine);

    path("/usuarios/me", () -> {
      before("/*", usuarioController::validarUsuario);

      get("/vinculaciones", miembroController::vinculaciones, templateEngine);
      post("/vinculaciones", miembroController::proponerVinculacion, templateEngine);

      path("/vinculaciones/:vinculacion", () -> {
        before("/*", miembroController::validarVinculacion);

        get("/trayectos", miembroController::trayectos, templateEngine);
        get("/trayectos/nuevo", miembroController::nuevoTrayecto, templateEngine);
        post("/trayectos", miembroController::anadirTrayecto, templateEngine);
        get("/trayectos/nuevo/tramos/nuevo", miembroController::nuevoTramo, templateEngine);
        post("/trayectos/nuevo/tramos", miembroController::anadirTramo, templateEngine);
      });
    });

    path("/organizaciones/me", () -> {
      before("/*", usuarioController::validarUsuario);

      get("/vinculaciones", organizacionController::vinculaciones, templateEngine);
      post("/vinculaciones", organizacionController::aceptarVinculacion, templateEngine);
      get("/da", organizacionController::da, templateEngine);
      post("/da", organizacionController::cargarDA, templateEngine);
      get("/reportes/individual", organizacionController::reportesIndividual, templateEngine);
      get("/reportes/evolucion", organizacionController::reportesEvolucion, templateEngine);
    });

    path("/sectoresterritoriales/me", () -> {
      before("/*", usuarioController::validarUsuario);

      get("/reportes/consumo/individual", agenteSectorialController::reportesConsumoIndividual, templateEngine);
      get("/reportes/consumo/evolucion", agenteSectorialController::reportesConsumoEvolucion, templateEngine);
      get("/reportes/organizacion/individual", agenteSectorialController::reportesOrganizacionIndividual, templateEngine);
      get("/reportes/organizacion/evolucion", agenteSectorialController::reportesOrganizacionEvolucion, templateEngine);
    });

    after("/*", (req, res) -> PerThreadEntityManagers.getEntityManager().clear());

    notFound((req, res) -> templateEngine.render(new ModelAndView(ImmutableMap.of(), "pages/404.html.hbs")));

    exception(ValidacionException.class, (e, req, res) -> {
      if (e.getErrores().contains("UNAUTHORIZED")) {
        res.redirect("/login?uriunautorized="+ req.uri());
      } else {
        res.body(templateEngine.render(new ModelAndView(ImmutableMap.of(), "pages/404.html.hbs")));
      }
    });
    exception(HttpNotFoundException.class, (e, req, res) -> {
      res.body(templateEngine.render(new ModelAndView(ImmutableMap.of(), "pages/404.html.hbs")));
    });

    exception(Exception.class, (e, req, res) -> {
      e.printStackTrace();
      res.body(templateEngine.render(new ModelAndView(ImmutableMap.of(), "pages/500.html.hbs")));
    });

  }

  private static Integer getPort() {
    return Optional.ofNullable(System.getenv("PORT"))
        .map(Integer::parseInt)
        .orElse(8080);
  }
}
