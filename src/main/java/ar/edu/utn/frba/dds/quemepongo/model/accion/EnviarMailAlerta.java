package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.dependencies.MailSender;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Clima;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.stream.Collectors;

public class EnviarMailAlerta implements Accion {
  private MailSender mailSender;

  public EnviarMailAlerta(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void emitirA(Usuario usuario, Clima clima) {
    mailSender.send(
        usuario.getEmail(),
        "¡Alerta meteorológica! Hay probabilidades de: " + clima.getAlertas().stream()
            .map(Alerta::toString)
            .collect(Collectors.joining(", "))
    );
  }
}
