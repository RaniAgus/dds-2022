package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.*;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.mail.MessagingException;
import java.io.IOException;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class ServicioDeNotificacionTest  extends BaseTest {

  @Test
  public void verificarComunicacionEntreNotificadores() throws MessagingException, IOException {
    Notificador mockMail = mock(NotificadorPorMail.class);
    Notificador mockWpp = mock(NotificadorPorWhatsApp.class);

    Contacto primerContacto = new Contacto("mailito@dds.com", "1163562354", asList(mockMail, mockWpp));
    Contacto segundoContacto = new Contacto("mailito@dds.com", "1163562354", asList(mockMail, mockWpp));

    Organizacion utn = crearOrganizacionVacia();
    utn.agregarContacto(primerContacto);
    utn.agregarContacto(segundoContacto);
    utn.enviarGuia("linkprueba.com");

    Mockito.verify(mockMail,times(2)).enviarGuiaRecomendacion(any(),any());
    Mockito.verify(mockWpp,times(2)).enviarGuiaRecomendacion(any(),any());
  }
}
