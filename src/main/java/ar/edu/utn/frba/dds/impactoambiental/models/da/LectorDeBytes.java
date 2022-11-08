package ar.edu.utn.frba.dds.impactoambiental.models.da;

import java.util.Arrays;
import java.util.List;

public class LectorDeBytes implements Lector {
  private final byte[] bytes;

  public LectorDeBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  @Override
  public List<String> getLineas() {
    return Arrays.asList(new String(bytes).split("\n"));
  }
}
