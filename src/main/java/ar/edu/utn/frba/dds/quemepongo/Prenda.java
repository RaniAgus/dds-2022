package ar.edu.utn.frba.dds.quemepongo;

import java.awt.*;

public class Prenda {
  private Tipo tipo;
  private Tela tela;
  private Color colorPrimario;
  private Color colorSecundario;

  public Prenda(Tipo tipo, Tela tela, Color colorPrimario, Color colorSecundario) {
    this.tipo = tipo;
    this.tela = tela;
    this.colorPrimario = colorPrimario;
    this.colorSecundario = colorSecundario;
  }

  public Prenda(Tipo tipo, Tela tela, Color colorPrimario) {
    this(tipo, tela, colorPrimario, null);
  }

  public Categoria getCategoria() {
    return this.tipo.getCategoria();
  }
}
