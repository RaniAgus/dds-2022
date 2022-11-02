package ar.edu.utn.frba.dds.impactoambiental.controllers.helpers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoEnTransportePublico;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoPrivado;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioDeLineas;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioMediosDeTransporte;

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

    Long origenID = Long.parseLong(form.getParam("origen").get());
    Parada origen = linea.getParadas().stream().filter(p -> p.getId().equals(origenID)).findFirst().get();
    Long destinoID = Long.parseLong(form.getParam("destino").get());
    Parada destino = linea.getParadas().stream().filter(p -> p.getId().equals(destinoID)).findFirst().get();

    return new TramoEnTransportePublico(origen, destino, linea);
  }

  public TramoPrivado generatePreTramoPrivado(Form form, Geolocalizador geolocalizador) {
    MedioDeTransporte medio = obtenerMedioDeTransporte(form).getValor();

    String paisOrigen = form.getParam("paisOrigen").get();
    String provinciaOrigen = form.getParam("provinciaOrigen").get();
    String municipioOrigen = form.getParam("municipioOrigen").get();
    String localidadOrigen = form.getParam("localidadOrigen").get();
    String calleOrigen = form.getParam("calleOrigen").get();
    String alturaOrigen = form.getParam("alturaOrigen").get();

    Ubicacion origen = geolocalizador.getUbicacion(
        paisOrigen,
        provinciaOrigen,
        municipioOrigen,
        localidadOrigen,
        calleOrigen,
        alturaOrigen
    ).get();

    String paisDestino = form.getParam("paisDestino").get();
    String provinciaDestino = form.getParam("provinciaDestino").get();
    String municipioDestino = form.getParam("municipioDestino").get();
    String localidadDestino = form.getParam("localidadDestino").get();
    String calleDestino = form.getParam("calleDestino").get();
    String alturaDestino = form.getParam("alturaDestino").get();

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
}
