package fs;

import fs.exception.CanNotOpenFileException;
import fs.model.File;
import fs.model.FileStatus;
import fs.model.HighLevelFileSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
  void sePuedeSaberSiUnPathEsUnArchivoRegular() {
    when(lowLevelFileSystem.exists("archivo.txt")).thenReturn(true);
    when(lowLevelFileSystem.isDirectory("archivo.txt")).thenReturn(false);
    when(lowLevelFileSystem.isRegularFile("archivo.txt")).thenReturn(true);
    Assertions.assertEquals(FileStatus.REGULAR_FILE, fileSystem.getStatusOf("archivo.txt"));
  }

  @Test
  void sePuedeSaberSiUnPathEsUnDirectorio() {
    when(lowLevelFileSystem.exists("/usr/bin")).thenReturn(true);
    when(lowLevelFileSystem.isDirectory("/usr/bin")).thenReturn(true);
    when(lowLevelFileSystem.isRegularFile("/usr/bin")).thenReturn(false);
    Assertions.assertEquals(FileStatus.DIRECTORY, fileSystem.getStatusOf("/usr/bin"));
  }

  @Test
  void sePuedeSaberSiUnPathExiste() {
    when(lowLevelFileSystem.exists("vinculo.lnk")).thenReturn(true);
    when(lowLevelFileSystem.isDirectory("vinculo.lnk")).thenReturn(false);
    when(lowLevelFileSystem.isRegularFile("vinculo.lnk")).thenReturn(false);
    Assertions.assertNotEquals(FileStatus.NON_EXISTENT, fileSystem.getStatusOf("vinculo.lnk"));
  }

  @Test
  void sePuedeSaberSiUnPathNoExiste() {
    when(lowLevelFileSystem.exists("inexistente")).thenReturn(false);
    when(lowLevelFileSystem.isDirectory("inexistente")).thenReturn(false);
    when(lowLevelFileSystem.isRegularFile("inexistente")).thenReturn(false);
    Assertions.assertEquals(FileStatus.NON_EXISTENT, fileSystem.getStatusOf("inexistente"));
  }
}
