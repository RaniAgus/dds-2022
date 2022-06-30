package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.dependencies.notificaciones.MailSender;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.Set;
import java.util.stream.Collectors;

public class EnviarMailAlertas implements AccionTrasAlertas {
  private MailSender mailSender;

  public EnviarMailAlertas(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void anteNuevasAlertas(Usuario usuario, Set<Alerta> alertas) {
    mailSender.send(
        usuario.getEmail(),
        "¡Alerta meteorológica! Hay probabilidades de: "
            + alertas.stream()
            .map(Alerta::toString)
            .collect(Collectors.joining(", "))
    );
  }
}
