package ar.edu.utn.frba.dds.impactoambiental.controllers.helpers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import java.time.LocalDate;
import java.util.List;
import spark.Request;

public class TrayectosHelper {
  private MiembrosHelper miembrosHelper = new MiembrosHelper();

  public Either<Trayecto> generateTrayecto(Request request, Form form) {
    List<Tramo> pretramos = miembrosHelper.obtenerPretramos(request);
    if (pretramos.isEmpty()) {
      return Either.fallido("El trayecto necesita al menos un tramo");
    }

    LocalDate fechaTrayecto = LocalDate.parse(form.getParam("fecha").get());
    return Either.exitoso(new Trayecto(fechaTrayecto, pretramos));
  }
}
