package ar.edu.utn.frba.dds.impactoambiental.controllers.helpers;

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
import spark.Request;

// TODO: Ver cómo hacer que la Request (o el parámetro que se reciba en su lugar) sea mockeable
public class MiembrosHelper {
  private RepositorioOrganizaciones repositorioOrganizaciones = RepositorioOrganizaciones.getInstance();

  public UsuarioMiembro usuarioMiembroDeSesion(Request req) {
    return req.session().attribute("usuario");
  }

  public Either<Miembro> obtenerMiembro(Request req) {
    return Either.desdeString(req.params("vinculacion"), "No se especificó una vinculación")
        .apply(Long::parseLong, "El id de la vinculación debe ser un número")
        .flatApply(usuarioMiembroDeSesion(req)::getMiembro, "No se encontró el miembro");
  }

  public List<VinculacionDto> obtenerVinculacionesDto(Request req) {
    List<VinculacionDto> vinculaciones = usuarioMiembroDeSesion(req).getMiembros().stream()
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

  public List<TrayectoResumenDto> obtenerTrayectosDto(Request req) {
    return obtenerMiembro(req).getValor().getTrayectos().stream()
        .map(t -> new TrayectoResumenDto(
            t.getFecha(),
            t.getCodigoInvite(),
            t.getTramos().get(0).nombreOrigen(),
            t.getTramos().get(t.getTramos().size() - 1).nombreDestino()
        ))
        .collect(Collectors.toList());
  }

  public List<Tramo> obtenerPretramos(Request req) {
    Map<Miembro, List<Tramo>> miembrosPretramos = req.session().attribute("pretramos");
    if (miembrosPretramos == null) {
      miembrosPretramos = new HashMap<>();
      req.session().attribute("pretramos", miembrosPretramos);
    }

    return miembrosPretramos.computeIfAbsent(obtenerMiembro(req).getValor(), k -> new ArrayList<>());
  }
}
