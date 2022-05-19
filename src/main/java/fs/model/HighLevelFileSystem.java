package fs.model;

import fs.exception.CanNotOpenFileException;
import fs.LowLevelFileSystem;

public class HighLevelFileSystem {
  private LowLevelFileSystem fileSystem;

  public HighLevelFileSystem(LowLevelFileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  public File open(String path) {
    int descriptor = fileSystem.openFile(path);
    if (descriptor == -1) {
      throw new CanNotOpenFileException("No se pudo abrir el archivo: " + path);
    }
    return new File(fileSystem, descriptor);
  }
}
