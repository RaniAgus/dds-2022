package ar.edu.utn.frba.dds.quemepongo.repository;

import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {
  private List<Usuario> usuarios = new ArrayList<>();

  public List<Usuario> getAll() {
    return usuarios;
  }
}
