package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.notificaciones.Mailer;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.Set;

public class EnviarMailAlertas implements AccionTrasAlertas {
  private Mailer mailer;

  public EnviarMailAlertas(Mailer mailer) {
    this.mailer = mailer;
  }

  @Override
  public void anteNuevasAlertas(Usuario usuario, Set<Alerta> alertas) {
    mailer.enviarAlertas(usuario.getEmail(), alertas);
  }
}
