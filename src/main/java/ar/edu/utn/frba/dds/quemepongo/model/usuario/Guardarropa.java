package ar.edu.utn.frba.dds.quemepongo.model.usuario;

import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Categoria;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Prenda;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Guardarropa {
  private ServicioMeteorologico servicioMeteorologico;
  private List<Prenda> prendas;
  private List<SolicitudModificacion> solicitudes;

  public Guardarropa(ServicioMeteorologico servicioMeteorologico,
                     List<Prenda> prendas,
                     List<SolicitudModificacion> solicitudes) {
    this.servicioMeteorologico = servicioMeteorologico;
    this.prendas = prendas;
    this.solicitudes = solicitudes;
  }

  public void solicitar(SolicitudModificacion solicitud) {
    solicitudes.add(solicitud);
  }

  public List<SolicitudModificacion> getSolicitudesPendientes() {
    return getSolicitudes(EstadoModificacion.PENDIENTE);
  }

  public List<SolicitudModificacion> getSolicitudesAceptadas() {
    return getSolicitudes(EstadoModificacion.ACEPTADA);
  }

  public void agregar(Prenda prenda) {
    prendas.add(prenda);
  }

  public void quitar(Prenda prenda) {
    prendas.remove(prenda);
  }

  public Stream<Prenda> getPrendasSugeribles(Categoria categoria) {
    return prendas.stream()
        .filter(Prenda::esSugerible)
        .filter(it -> it.esAptaPara(servicioMeteorologico.getTemperatura()))
        .filter(it -> it.esDeCategoria(categoria));
  }

  private List<SolicitudModificacion> getSolicitudes(EstadoModificacion estado) {
    return solicitudes.stream()
        .filter(it -> it.estaEnEstado(estado))
        .collect(Collectors.toList());
  }
}
