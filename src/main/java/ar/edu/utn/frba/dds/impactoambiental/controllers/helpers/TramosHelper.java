package ar.edu.utn.frba.dds.impactoambiental.controllers.helpers;

import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoEnTransportePublico;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoPrivado;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioDeLineas;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioMediosDeTransporte;
import spark.Request;

public class TramosHelper {
  private RepositorioDeLineas repositorioDeLineas = RepositorioDeLineas.getInstance();
  private RepositorioMediosDeTransporte repositorioMediosDeTransporte = RepositorioMediosDeTransporte.getInstance();

  public Either<Linea> obtenerLinea(Request req) {
    return Form.of(req).getParamOrError("linea", "Es necesario indicar una linea")
        .apply(Long::parseLong, "El id de la linea debe ser un numero")
        .flatApply(repositorioDeLineas::obtenerPorID, "La linea no existe");
  }

  public Tramo generatePreTramoPublico(Request request) {
    Linea linea = obtenerLinea(request).getValor();

    Long origenID = Long.parseLong(Form.of(request).getParam("origen").get());
    Parada origen = linea.getParadas().stream().filter(p -> p.getId() == origenID).findFirst().get();
    Long destinoID = Long.parseLong(Form.of(request).getParam("destino").get());
    Parada destino = linea.getParadas().stream().filter(p -> p.getId() == destinoID).findFirst().get();

    return (new TramoEnTransportePublico(origen, destino, linea));
  }

  public Either<MedioDeTransporte> obtenerMedioDeTransporte(Request req) {
    return Form.of(req).getParamOrError("medioDeTransporte", "Es necesario indicar un medio de transporte")
        .apply(s -> repositorioMediosDeTransporte.obtenerPorID(Long.parseLong(s)).get(), "El medio de transporte no existe");
  }

  public TramoPrivado generatePreTramoPrivado(Request request, Geolocalizador geolocalizador) {
    //params del form
    MedioDeTransporte medio = obtenerMedioDeTransporte(request).getValor();

    String paisOrigen = Form.of(request).getParam("paisOrigen").get();
    String provinciaOrigen = Form.of(request).getParam("provinciaOrigen").get();
    String municipioOrigen = Form.of(request).getParam("municipioOrigen").get();
    String localidadOrigen = Form.of(request).getParam("localidadOrigen").get();
    String calleOrigen = Form.of(request).getParam("calleOrigen").get();
    String alturaOrigen = Form.of(request).getParam("alturaOrigen").get();

    Ubicacion origen = geolocalizador.getUbicacion(
        paisOrigen,
        provinciaOrigen,
        municipioOrigen,
        localidadOrigen,
        calleOrigen,
        alturaOrigen
    ).get();

    String paisDestino = Form.of(request).getParam("paisDestino").get();
    String provinciaDestino = Form.of(request).getParam("provinciaDestino").get();
    String municipioDestino = Form.of(request).getParam("municipioDestino").get();
    String localidadDestino = Form.of(request).getParam("localidadDestino").get();
    String calleDestino = Form.of(request).getParam("calleDestino").get();
    String alturaDestino = Form.of(request).getParam("alturaDestino").get();

    Ubicacion destino = geolocalizador.getUbicacion(
        paisDestino,
        provinciaDestino,
        municipioDestino,
        localidadDestino,
        calleDestino,
        alturaDestino
    ).get();

    return (new TramoPrivado(geolocalizador, origen, destino, medio));
  }
}
