package fs.model;

import fs.exception.BufferOutOfBoundsException;

public class Buffer {
  private byte[] bytes;
  private int start;
  private int end;
  private int size;

  public Buffer(int size) {
    this.bytes = new byte[size];
    this.size = size;
    this.start = 0;
    this.end = size - 1;
  }

  public void limit(int offset) {
    if (getMaxSize() <= start + offset - 1) {
      throw new BufferOutOfBoundsException(
          "El limite sobrepasa la capacidad maxima del buffer: "
              + (start + offset - 1) + "(max: " + getMaxSize() + ")"
      );
    }
    end = start + offset - 1;
  }

  public int getCurrentSize() {
    return end - start + 1;
  }

  public int getMaxSize() {
    return size;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  public byte[] getBytes() {
    return bytes;
  }
}
