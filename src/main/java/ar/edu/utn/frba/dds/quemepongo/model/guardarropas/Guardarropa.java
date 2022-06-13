package ar.edu.utn.frba.dds.quemepongo.model.guardarropas;

import ar.edu.utn.frba.dds.quemepongo.model.prenda.Categoria;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Prenda;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

  public Atuendo sugerir() {
    return new Atuendo(
      sugerirAleatoriamente(Categoria.PARTE_SUPERIOR),
      sugerirAleatoriamente(Categoria.PARTE_INFERIOR),
      sugerirAleatoriamente(Categoria.CALZADO),
      sugerirAleatoriamente(Categoria.ACCESORIO)
    );
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

  private Prenda sugerirAleatoriamente(Categoria categoria) {
    List<Prenda> prendasPosibles = getPrendasSugeribles(categoria);
    return prendasPosibles.get(
        ThreadLocalRandom.current().nextInt(prendasPosibles.size())
            % prendasPosibles.size()
    );
  }

  private List<Prenda> getPrendasSugeribles(Categoria categoria) {
    return prendas.stream()
        .filter(Prenda::esSugerible)
        .filter(it -> it.esAptaPara(servicioMeteorologico.getTemperatura()))
        .filter(it -> it.esDeCategoria(categoria))
        .collect(Collectors.toList());
  }

  private List<SolicitudModificacion> getSolicitudes(EstadoModificacion estado) {
    return solicitudes.stream()
        .filter(it -> it.estaEnEstado(estado))
        .collect(Collectors.toList());
  }
}
