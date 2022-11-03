package ar.edu.utn.frba.dds.impactoambiental.controllers.helpers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoEnTransportePublico;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoPrivado;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioDeLineas;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioMediosDeTransporte;
import java.util.List;
import java.util.Map;
import spark.Request;

public class TramosHelper {
  private RepositorioDeLineas repositorioDeLineas = RepositorioDeLineas.getInstance();
  private RepositorioMediosDeTransporte repositorioMediosDeTransporte = RepositorioMediosDeTransporte.getInstance();

  public Either<Linea> obtenerLinea(Form form) {
    return form.getParamOrError("linea", "Es necesario indicar una linea")
        .apply(Long::parseLong, "El id de la linea debe ser un numero")
        .flatApply(repositorioDeLineas::obtenerPorID, "La linea no existe");
  }

  public Either<MedioDeTransporte> obtenerMedioDeTransporte(Form form) {
    return form.getParamOrError("medioDeTransporte", "Es necesario indicar un medio de transporte")
        .apply(Long::parseLong, "El id del medio de transporte debe ser un numero")
        .flatApply(repositorioMediosDeTransporte::obtenerPorID, "El medio de transporte no existe");
  }

  public Tramo generatePreTramoPublico(Form form) {
    Linea linea = obtenerLinea(form).getValor();

    Long origenID = form.getParamOrError("origen", "Es necesario indicar un origen")
        .apply(Long::parseLong, "El id del origen debe ser un numero")
        .getValor();

    Parada origen = linea.getParadas().stream().filter(p -> p.getId().equals(origenID)).findFirst().get();

    Long destinoID = form.getParamOrError("destino", "Es necesario indicar un destino")
        .apply(Long::parseLong, "El id del destino debe ser un numero")
        .getValor();

    Parada destino = linea.getParadas().stream().filter(p -> p.getId().equals(destinoID)).findFirst().get();

    return new TramoEnTransportePublico(origen, destino, linea);
  }

  public TramoPrivado generatePreTramoPrivado(Form form, Geolocalizador geolocalizador) {
    MedioDeTransporte medio = obtenerMedioDeTransporte(form).getValor();

    String paisOrigen = form.getParamOrError("paisOrigen", "Es necesario indicar un pais de origen").getValor();
    String provinciaOrigen = form.getParamOrError("provinciaOrigen", "Es necesario indicar una provincia de origen").getValor();
    String municipioOrigen = form.getParamOrError("municipioOrigen", "Es necesario indicar un municipio de origen").getValor();
    String localidadOrigen = form.getParamOrError("localidadOrigen", "Es necesario indicar una localidad de origen").getValor();
    String calleOrigen = form.getParamOrError("calleOrigen", "Es necesario indicar una calle de origen").getValor();
    String alturaOrigen = form.getParamOrError("alturaOrigen", "Es necesario indicar una altura de origen").getValor();

    Ubicacion origen = geolocalizador.getUbicacion(
        paisOrigen,
        provinciaOrigen,
        municipioOrigen,
        localidadOrigen,
        calleOrigen,
        alturaOrigen
    ).get();

    String paisDestino = form.getParamOrError("paisDestino", "Es necesario indicar un pais de destino").getValor();
    String provinciaDestino = form.getParamOrError("provinciaDestino", "Es necesario indicar una provincia de destino").getValor();
    String municipioDestino = form.getParamOrError("municipioDestino", "Es necesario indicar un municipio de destino").getValor();
    String localidadDestino = form.getParamOrError("localidadDestino", "Es necesario indicar una localidad de destino").getValor();
    String calleDestino = form.getParamOrError("calleDestino", "Es necesario indicar una calle de destino").getValor();
    String alturaDestino = form.getParamOrError("alturaDestino", "Es necesario indicar una altura de destino").getValor();

    Ubicacion destino = geolocalizador.getUbicacion(
        paisDestino,
        provinciaDestino,
        municipioDestino,
        localidadDestino,
        calleDestino,
        alturaDestino
    ).get();

    return new TramoPrivado(geolocalizador, origen, destino, medio);
  }

  public void limpiarPretramos(Miembro miembro, Request req) {
    Map<Long, List<Tramo>> miembrosPretramos = req.session().attribute("miembrosPretramos");
    if (miembrosPretramos != null) {
      miembrosPretramos.remove(miembro.getId());
    }
  }
}
