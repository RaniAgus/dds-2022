package models;

import java.util.*;
import java.util.stream.Collectors;


public class Sector {
  private List<Vinculacion> vinculaciones;

  public Sector(List<Vinculacion> vinculaciones) {
    this.vinculaciones = new ArrayList<>(vinculaciones);
  }

  public void solicitarVinculacion(Miembro miembro) {
    if (getVinculacionConMiembro(miembro).isPresent()) throw new IllegalArgumentException("El miembro ya tiene una vinculacion asociada en el sector");
    this.vinculaciones.add(new Vinculacion(miembro));
  }

  private Optional<Vinculacion> getVinculacionConMiembro(Miembro miembro) {
    return this.vinculaciones.stream().filter(vinculacion -> vinculacion.getMiembro() == miembro).findFirst();
  }

  public void vincularMiembro(Miembro miembro) {
    Optional<Vinculacion> aux = this.getVinculacionConMiembro(miembro);
    if (!aux.isPresent()) throw new IllegalArgumentException("El miembro no solicito vincularse.");
    if (aux.get().getEstado() == EstadoVinculo.ACEPTADO) throw new IllegalArgumentException("El miembro ya habia sido aceptado");
    aux.get().setEstado(EstadoVinculo.ACEPTADO);
  }

  public List<Miembro> getMiembrosSegunEstado(EstadoVinculo estado) {
    return this.vinculaciones.stream()
        .filter(vinculacion -> vinculacion.getEstado() == estado)
        .map(Vinculacion::getMiembro)
        .collect(Collectors.toList());
  }

}
