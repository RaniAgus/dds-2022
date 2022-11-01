package ar.edu.utn.frba.dds.impactoambiental.repositories;

import java.util.Optional;

import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;

public class RepositorioOrganizaciones implements Repositorio<Organizacion> {
  private static final RepositorioOrganizaciones instance = new RepositorioOrganizaciones();

  public static RepositorioOrganizaciones getInstance() {
    return instance;
  }

  private RepositorioOrganizaciones() {}

  @Override
  public Class<Organizacion> clase() {
    return Organizacion.class;
  }

  public Optional<Organizacion> buscarPorMiembro(Miembro miembro) {
    return createQuery(
      "SELECT o FROM Organizacion as o " +
      "JOIN o.sectores as s " +
      "JOIN s.vinculaciones as v " +
      "WHERE v.miembro = :miembro",
      Organizacion.class
    ).setParameter("miembro", miembro).getResultStream().findFirst();
  }
}
