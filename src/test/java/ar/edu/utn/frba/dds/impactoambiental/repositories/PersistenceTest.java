package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.ServiceLocator;
import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public interface PersistenceTest extends TransactionalOps, EntityManagerOps, WithGlobalEntityManager {
  @BeforeAll
  static void init() {
    ServiceLocator serviceLocatorMock = mock(ServiceLocator.class);
    when(serviceLocatorMock.getRecomendacionesTemplate()).thenReturn("");
    when(serviceLocatorMock.getWhatsappApiKey()).thenReturn("");
    when(serviceLocatorMock.getWhatsappApiId()).thenReturn("");
    when(serviceLocatorMock.getSmtpPassword()).thenReturn("");
    when(serviceLocatorMock.getSmtpUser()).thenReturn("");
    when(serviceLocatorMock.getRecomendacionesUrl()).thenReturn("");
    when(serviceLocatorMock.getWeakPasswordsFileReader()).thenReturn(mock(LectorDeArchivos.class));
    ServiceLocator.setServiceLocator(serviceLocatorMock);
  }

  @BeforeEach
  default void setup() {
    beginTransaction();
  }

  @AfterEach
  default void teardown() {
    rollbackTransaction();
  }
}
