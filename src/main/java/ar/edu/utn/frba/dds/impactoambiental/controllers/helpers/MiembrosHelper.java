package ar.edu.utn.frba.dds.impactoambiental.controllers.helpers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.dtos.TrayectoResumenDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
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

  public Either<Miembro> obtenerMiembroDesdeAttributes(Context ctx) {
    return ctx.<Vinculacion>getRequestAttribute("vinculacion", "INTERNAL_SERVER_ERROR")
        .map(Vinculacion::getMiembro);
  }

  public List<VinculacionDto> obtenerVinculacionesDto(Context ctx) {
    return usuarioMiembroDeSesion(ctx).getValor().getMiembros().stream()
        .map(m -> VinculacionDto.from(repositorioOrganizaciones.buscarPorMiembro(m).get(), m))
        .collect(Collectors.toList());
  }

  public List<TrayectoResumenDto> obtenerTrayectosDto(Context ctx) {
    return obtenerMiembroDesdeAttributes(ctx).getValor().getTrayectos().stream()
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
    return miembrosPretramos.computeIfAbsent(obtenerMiembroDesdeAttributes(ctx).getValor().getId(), k -> new ArrayList<>());
  }
}
