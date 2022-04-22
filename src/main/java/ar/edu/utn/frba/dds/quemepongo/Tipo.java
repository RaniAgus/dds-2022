package ar.edu.utn.frba.dds.quemepongo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Tipo {
  private String nombre;
  private @Getter Categoria categoria;
}
