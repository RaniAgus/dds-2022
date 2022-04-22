package ar.edu.utn.frba.dds.quemepongo;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public class Prenda {
  private Tipo tipo;
  private Tela tela;
  private Color colorPrimario;
  private Color colorSecundario;

  public Prenda(Tipo tipo, Tela tela, Color colorPrimario) {
    this(tipo, tela, colorPrimario, null);
  }

  public Categoria getCategoria() {
    return this.tipo.getCategoria();
  }
}
