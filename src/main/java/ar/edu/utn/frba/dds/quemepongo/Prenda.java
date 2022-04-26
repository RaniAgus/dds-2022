package ar.edu.utn.frba.dds.quemepongo;

import java.awt.*;

public class Prenda {
  private Tipo tipo;
  private Material material;
  private Trama trama;
  private Color colorPrimario;
  private Color colorSecundario;

  public Prenda(Tipo tipo,
                Material material,
                Trama trama,
                Color colorPrimario,
                Color colorSecundario) {
    this.tipo = tipo;
    this.material = material;
    this.trama = trama;
    this.colorPrimario = colorPrimario;
    this.colorSecundario = colorSecundario;
  }

  public Categoria getCategoria() {
    return tipo.getCategoria();
  }
}
