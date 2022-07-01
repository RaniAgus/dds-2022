package ar.edu.utn.frba.dds.quemepongo.repository;

import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {
  private static final RepositorioUsuarios INSTANCE = new RepositorioUsuarios();
  private List<Usuario> usuarios = new ArrayList<>();

  private RepositorioUsuarios() {
  }

  public static RepositorioUsuarios getInstance() {
    return INSTANCE;
  }

  public List<Usuario> getAll() {
    return usuarios;
  }
}
