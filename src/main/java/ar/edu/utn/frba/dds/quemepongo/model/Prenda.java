package ar.edu.utn.frba.dds.quemepongo.model;

import ar.edu.utn.frba.dds.quemepongo.model.estado.Estado;

import java.awt.*;

public class Prenda {
  private Tipo tipo;
  private Material material;
  private Trama trama;
  private Color colorPrimario;
  private Color colorSecundario;
  private Estado estado;
  private boolean estaLavandose;

  public Prenda(Tipo tipo,
                Material material,
                Trama trama,
                Color colorPrimario,
                Color colorSecundario,
                Estado estado,
                boolean estaLavandose) {
    this.tipo = tipo;
    this.material = material;
    this.trama = trama;
    this.colorPrimario = colorPrimario;
    this.colorSecundario = colorSecundario;
    this.estado = estado;
    this.estaLavandose = estaLavandose;
  }

  public boolean esDeCategoria(Categoria categoria) {
    return tipo.getCategoria().equals(categoria);
  }

  public boolean esSugerible() {
    return !estaLavandose && estado.esSugerible();
  }

  public void usar() {
    estado = estado.usar();
  }

  public void ponerALavar() {
    estaLavandose = true;
  }

  public void lavar() {
    estado = estado.lavar();
    estaLavandose = false;
  }
}
