package ar.edu.utn.frba.dds.quemepongo.repository;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Clima;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Temperatura;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {
  private List<Usuario> usuarios = new ArrayList<>();

  public RepositorioUsuarios agregar(Usuario usuario) {
    usuarios.add(usuario);
    return this;
  }

  public void generarSugerencias(Temperatura temperatura) {
    usuarios.forEach(it -> it.generarSugerencia(temperatura));
  }

  public void emitirAlertas(Clima clima) {
    if (clima.tieneAlertas()) {
      usuarios.forEach(it -> it.emitirAlerta(clima));
    }
  }
}
