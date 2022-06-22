package calendarios;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RecordatorioTest extends BaseTest {
  // 9. Permitir asignarle a un evento varios recordatorios, que se enviarán cuando falte un cierto tiempo

  @Test
  public void unRecordatorioSeEnviaAlOwnerYSusInvitadosSiAlEventoLeFaltaLaDuracionEstipulada() {
    Usuario invitado = crearUsuario("quiensera@gugle.com.ar");
    Recordatorio recordatorio = crearRecordatorio(Duration.ofMinutes(10));
    Evento tpOperativos = crearEventoSimpleConRecordatorios(LocalDateTime.now().plus(Duration.ofMinutes(10)), singletonList(recordatorio), singletonList(invitado));

    Usuario owner = crearUsuario("sway@gugle.com.ar");
    tpOperativos.enviarRecordatorios(owner);

    verify(shemailLib, times(1)).enviarMailA(eq(owner.getEmail()), any(), any());
    verify(shemailLib, times(1)).enviarMailA(eq(invitado.getEmail()), any(), any());
  }

  @Test
  public void unRecordatorioNoSeEnviaSiAlEventoNoLeFaltaLaDuracionEstipulada() {
    Usuario invitado = crearUsuario("quiensera@gugle.com.ar");
    Recordatorio recordatorio = crearRecordatorio(Duration.ofMinutes(10));
    Evento tpOperativos = crearEventoSimpleConRecordatorios(LocalDateTime.now().plus(Duration.ofMinutes(12)), singletonList(recordatorio), singletonList(invitado));

    Usuario owner = crearUsuario("sway@gugle.com.ar");
    tpOperativos.enviarRecordatorios(owner);

    verify(shemailLib, never()).enviarMailA(eq(owner.getEmail()), any(), any());
    verify(shemailLib, never()).enviarMailA(eq(invitado.getEmail()), any(), any());
  }
}
