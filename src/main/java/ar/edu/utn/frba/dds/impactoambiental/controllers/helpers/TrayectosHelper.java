package ar.edu.utn.frba.dds.impactoambiental.controllers.helpers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Validador;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import java.time.LocalDate;
import java.util.List;

public class TrayectosHelper {
  private MiembrosHelper miembrosHelper = new MiembrosHelper();

  public Either<Trayecto> generateTrayecto(Context ctx, Form form) {
    Either<List<Tramo>> pretramos = Either.exitoso(miembrosHelper.obtenerPretramos(ctx))
        .flatMap(p -> new Validador<>(p)
            .agregarValidacion(x -> !x.isEmpty(), "El trayecto necesita al menos un tramo")
            .validar());

    Either<LocalDate> fechaTrayecto = form.getParamOrError("fecha", "La fecha es requerida")
        .apply(LocalDate::parse, "La fecha debe ser una fecha");

    return Either.concatenar(() -> new Trayecto(fechaTrayecto.getValor(), pretramos.getValor()),
        fechaTrayecto, pretramos);
  }
}
