package fs.model;

import fs.LowLevelFileSystem;
import fs.exception.CanNotReadFileException;

import java.util.function.Consumer;

public class File {
  private LowLevelFileSystem fileSystem;
  private int descriptor;

  public File(LowLevelFileSystem fileSystem, int descriptor) {
    this.fileSystem = fileSystem;
    this.descriptor = descriptor;
  }

  public int getDescriptor() {
    return descriptor;
  }

  public void read(Buffer buffer) {
    int bytes = fileSystem.syncReadFile(descriptor, buffer.getBytes(), buffer.getStart(), buffer.getEnd());
    if (bytes == -1) {
      throw new CanNotReadFileException("No se pudo leer el archivo");
    }
    buffer.limit(bytes);
  }

  public void write(Buffer buffer) {
    fileSystem.syncWriteFile(descriptor, buffer.getBytes(), buffer.getStart(), buffer.getEnd());
  }

  public void close() {
    fileSystem.closeFile(descriptor);
  }
}
