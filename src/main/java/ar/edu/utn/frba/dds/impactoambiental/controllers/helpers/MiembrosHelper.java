package ar.edu.utn.frba.dds.impactoambiental.controllers.helpers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.dtos.TrayectoResumenDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioOrganizaciones;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MiembrosHelper {
  private RepositorioOrganizaciones repositorioOrganizaciones = RepositorioOrganizaciones.getInstance();

  public Either<UsuarioMiembro> usuarioMiembroDeSesion(Context ctx) {
    return ctx.getRequestAttribute("usuario", "No se encontró el usuario en la sesión");
  }

  // TODO: Ver por qué :vinculacion devuelve un id de miembro en lugar de un id de vinculacion
  public Either<Miembro> obtenerMiembro(Context ctx) {
    return ctx.getPathParam("vinculacion", "No se especificó una vinculación")
        .apply(Long::parseLong, "El id de la vinculación debe ser un número")
        .flatMap(id -> usuarioMiembroDeSesion(ctx).flatApply(u -> u.getMiembro(id), "No se encontró el miembro"));
  }

  public List<VinculacionDto> obtenerVinculacionesDto(Context ctx) {
    List<VinculacionDto> vinculaciones = usuarioMiembroDeSesion(ctx).getValor().getMiembros().stream()
        .map(m -> new VinculacionDto(
            null,
            m,
            repositorioOrganizaciones.buscarPorMiembro(m).get(),
            null,
            null
        )).collect(Collectors.toList());

    vinculaciones.forEach(v -> {
      v.setSector(v.getOrganizacion().getSectores().stream()
          .filter(s -> s.getAllMiembros().contains(v.getMiembro()))
          .findFirst().get());
      v.setEstado(v.getSector().getVinculaciones().stream()
          .filter(vinc -> vinc.getMiembro().equals(v.getMiembro()))
          .findFirst().get().getEstado());
      v.setId(v.getSector().getVinculaciones().stream().
          filter(vinc -> vinc.getMiembro().equals(v.getMiembro()))
          .findFirst().get().getId());
    });

    return vinculaciones;
  }

  public List<TrayectoResumenDto> obtenerTrayectosDto(Context ctx) {
    return obtenerMiembro(ctx).getValor().getTrayectos().stream()
        .map(t -> new TrayectoResumenDto(
            t.getFecha(),
            t.getCodigoInvite(),
            t.getTramos().get(0).nombreOrigen(),
            t.getTramos().get(t.getTramos().size() - 1).nombreDestino()
        ))
        .collect(Collectors.toList());
  }

  public List<Tramo> obtenerPretramos(Context ctx) {
    Map<Long, List<Tramo>> miembrosPretramos = ctx.computeSessionAttributeIfAbsent("miembrosPretramos", HashMap::new);
    return miembrosPretramos.computeIfAbsent(obtenerMiembro(ctx).getValor().getId(), k -> new ArrayList<>());
  }
}
