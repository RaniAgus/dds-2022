package fs;

import fs.exception.CanNotOpenFileException;
import fs.exception.CanNotReadFileException;
import fs.model.Buffer;
import fs.model.File;
import fs.model.HighLevelFileSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;

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
  void sePuedeLeerSincronicamenteUnArchivoCuandoNoHayNadaParaLeer() {
    Buffer buffer = new Buffer(100);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    when(lowLevelFileSystem.syncReadFile(42, buffer.getBytes(), 0, 100)).thenReturn(0);

    File file = fileSystem.open("ejemplo.txt");
    file.read(buffer);

    Assertions.assertEquals(0, buffer.getStart());
    Assertions.assertEquals(-1, buffer.getEnd());
    Assertions.assertEquals(0, buffer.getCurrentSize());
  }

  @Test
  void sePuedeLeerSincronicamenteUnArchivoCuandoHayAlgoParaLeer() {
    Buffer buffer = new Buffer(10);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    when(lowLevelFileSystem.syncReadFile(42, buffer.getBytes(), 0, 9)).thenAnswer(invocation -> {
      Arrays.fill(buffer.getBytes(), 0, 4, (byte) 3);
      return 4;
    });

    File file = fileSystem.open("ejemplo.txt");
    file.read(buffer);

    Assertions.assertEquals(0, buffer.getStart());
    Assertions.assertEquals(3, buffer.getEnd());
    Assertions.assertEquals(4, buffer.getCurrentSize());
    Assertions.assertArrayEquals(buffer.getBytes(), new byte[] {3, 3, 3, 3, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void siLaLecturaSincronicaFallaUnaExcepcionEsLanzada() {
    Buffer buffer = new Buffer(10);

    when(lowLevelFileSystem.openFile("archivoMalito.txt")).thenReturn(13);
    when(lowLevelFileSystem.syncReadFile(anyInt(), any(), anyInt(), anyInt())).thenReturn(-1);

    File file = fileSystem.open("archivoMalito.txt");

    Assertions.assertThrows(CanNotReadFileException.class, () -> file.read(buffer));
  }

  @Test
  void sePuedeEscribirSincronicamenteUnArchivoCuandoHayNoHayNadaParaEscribir() {
    Buffer buffer = new Buffer(100);
    buffer.limit(0);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    doNothing().when(lowLevelFileSystem).syncWriteFile(42, buffer.getBytes(), 0,  -1);

    File file = fileSystem.open("ejemplo.txt");
    file.write(buffer);

    verify(lowLevelFileSystem, times(1)).syncWriteFile(42, buffer.getBytes(), 0, -1);
  }

  @Test
  void sePuedeEscribirSincronicamenteUnArchivoCuandoHayAlgoParaEscribir() {
    Buffer buffer = new Buffer(100);
    Arrays.fill(buffer.getBytes(), 0, 4, (byte) 3);
    buffer.limit(5);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    doNothing().when(lowLevelFileSystem).syncWriteFile(42, buffer.getBytes(), 0,  4);

    File file = fileSystem.open("ejemplo.txt");
    file.write(buffer);

    verify(lowLevelFileSystem, times(1)).syncWriteFile(42, buffer.getBytes(), 0, 4);
  }

  @Test @SuppressWarnings("unchecked")
  void sePuedeLeerAsincronicamenteUnArchivo() {
    Buffer buffer = new Buffer(10);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    doAnswer(invocation -> {
      Arrays.fill(buffer.getBytes(), 0, 4, (byte) 3);
      invocation.getArgument(4, Consumer.class).accept(4);
      return null;
    }).when(lowLevelFileSystem).asyncReadFile(eq(42), eq(buffer.getBytes()), eq(0), eq(9), any());

    File file = fileSystem.open("ejemplo.txt");
    CompletableFuture<Buffer> future = file.readAsync(buffer);

    Assertions.assertEquals(0, future.join().getStart());
    Assertions.assertEquals(3, future.join().getEnd());
    Assertions.assertEquals(4, future.join().getCurrentSize());
    Assertions.assertArrayEquals(future.join().getBytes(), new byte[] {3, 3, 3, 3, 0, 0, 0, 0, 0, 0});
  }

  @Test @SuppressWarnings("unchecked")
  void siLaLecturaAsincronicaFallaUnaExcepcionEsLanzada() {
    Buffer buffer = new Buffer(10);

    when(lowLevelFileSystem.openFile("archivoMalito.txt")).thenReturn(13);
    doAnswer(invocation -> {
      invocation.getArgument(4, Consumer.class).accept(-1);
      return null;
    }).when(lowLevelFileSystem).asyncReadFile(anyInt(), any(), anyInt(), anyInt(), any());

    File file = fileSystem.open("archivoMalito.txt");
    CompletableFuture<Buffer> future = file.readAsync(buffer);

    CompletionException e = Assertions.assertThrows(CompletionException.class, future::join);
    Assertions.assertEquals(CanNotReadFileException.class, e.getCause().getClass());
  }

  @Test
  void sePuedeEscribirAsincronicamenteUnArchivo() {
    Buffer buffer = new Buffer(100);
    Arrays.fill(buffer.getBytes(), 0, 4, (byte) 3);
    buffer.limit(5);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    doAnswer(invocation -> {
      invocation.getArgument(4, Runnable.class).run();
      return null;
    }).when(lowLevelFileSystem).asyncWriteFile(eq(42), eq(buffer.getBytes()), eq(0),  eq(4), any());

    File file = fileSystem.open("ejemplo.txt");
    file.writeAsync(buffer).join();

    verify(lowLevelFileSystem, times(1)).asyncWriteFile(eq(42), eq(buffer.getBytes()), eq(0),  eq(4), any());
  }

  @Test
  void sePuedeCerrarUnArchivo() {
    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    doNothing().when(lowLevelFileSystem).closeFile(42);

    File file = fileSystem.open("ejemplo.txt");
    file.close();

    verify(lowLevelFileSystem, times(1)).closeFile(42);
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
