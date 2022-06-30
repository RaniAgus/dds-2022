package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import static ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios.usuarios;

public final class GeneradorDeSugerencias {
  public static void main(String[] args) {
    usuarios().getAll().forEach(Usuario::generarSugerencia);
  }
}
