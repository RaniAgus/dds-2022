package fs.model;

import fs.LowLevelFileSystem;
import fs.exception.CanNotReadFileException;
import java.util.concurrent.CompletableFuture;

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
    int bytes = fileSystem.syncReadFile(
        descriptor,
        buffer.getBytes(),
        buffer.getStart(),
        buffer.getEnd()
    );
    if (bytes == -1) {
      throw new CanNotReadFileException("No se pudo leer el archivo");
    }
    buffer.limit(bytes);
  }

  public void write(Buffer buffer) {
    fileSystem.syncWriteFile(
        descriptor,
        buffer.getBytes(),
        buffer.getStart(),
        buffer.getEnd()
    );
  }

  public CompletableFuture<Buffer> readAsync(Buffer buffer) {
    CompletableFuture<Buffer> future = new CompletableFuture<>();
    fileSystem.asyncReadFile(
        descriptor,
        buffer.getBytes(),
        buffer.getStart(),
        buffer.getEnd(),
        bytes -> {
            if (bytes == -1) {
              future.completeExceptionally(
                  new CanNotReadFileException("No se pudo leer el archivo")
              );
            } else {
              buffer.limit(bytes);
              future.complete(buffer);
            }
        }
    );
    return future;
  }

  public CompletableFuture<Buffer> writeAsync(Buffer buffer) {
    CompletableFuture<Buffer> future = new CompletableFuture<>();
    fileSystem.asyncWriteFile(
        descriptor,
        buffer.getBytes(),
        buffer.getStart(),
        buffer.getEnd(),
        () -> future.complete(buffer)
    );
    return future;
  }

  public void close() {
    fileSystem.closeFile(descriptor);
  }
}
