package calendarios;

import calendarios.servicios.ShemailLib;
import java.time.Duration;

public class Recordatorio {
  private ShemailLib shemailLib;
  private Duration duracion;

  public Recordatorio(ShemailLib shemailLib, Duration duracion) {
    this.shemailLib = shemailLib;
    this.duracion = duracion;
  }

  public boolean debeSerEnviado(Duration cuantoFalta) {
    return duracion.minus(cuantoFalta).isZero();
  }

  public void enviar(Usuario owner, EventoSimple evento) {
    shemailLib.enviarMailA(owner.getEmail(), evento.getNombre(), evento.toString());
    evento.getInvitados().forEach(
        it -> shemailLib.enviarMailA(it.getEmail(), evento.getNombre(), evento.toString()));
  }
}
