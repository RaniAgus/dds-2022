package fs;

import fs.exception.CanNotOpenFileException;
import fs.model.File;
import fs.model.HighLevelFileSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class HighLevelFileSystemTest {

  private LowLevelFileSystem lowLevelFileSystem;
  private HighLevelFileSystem fileSystem;

  @BeforeEach
  void initFileSystem() {
    lowLevelFileSystem = mock(LowLevelFileSystem.class);
    fileSystem = new HighLevelFileSystem(lowLevelFileSystem);
  }

  @Test
  void sePuedeAbrirUnArchivo() {
    when(lowLevelFileSystem.openFile("unArchivo.txt")).thenReturn(42);
    File file = fileSystem.open("unArchivo.txt");
    Assertions.assertEquals(file.getDescriptor(), 42);
  }

  @Test
  void siLaAperturaFallaUnaExcepcionEsLanzada() {
    when(lowLevelFileSystem.openFile("otroArchivo.txt")).thenReturn(-1);
    Assertions.assertThrows(CanNotOpenFileException.class, () -> fileSystem.open("otroArchivo.txt"));
  }


  @Test
  @Disabled("Punto Bonus")
  void sePuedeSaberSiUnPathEsUnArchivoRegular() {
    Assertions.fail("Completar: te va a convenir extraer una nueva abstracción para diferenciar a los paths de los archivos");
  }

  @Test
  @Disabled("Punto Bonus")
  void sePuedeSaberSiUnPathEsUnDirectorio() {
    Assertions.fail("Completar: te va a convenir extraer una nueva abstracción para diferenciar a los paths de los archivos");
  }

  @Test
  @Disabled("Punto Bonus")
  void sePuedeSaberSiUnPathExiste() {
    Assertions.fail("Completar: te va a convenir extraer una nueva abstracción para diferenciar a los paths de los archivos");
  }
}
