package ar.edu.utn.frba.dds.quemepongo.model.prenda;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Temperatura;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.estado.EstadoPrenda;

import java.awt.*;
import java.util.Set;

public class Prenda {
  private Tipo tipo;
  private Material material;
  private Trama trama;
  private Color colorPrimario;
  private Color colorSecundario;
  private EstadoPrenda estado;
  private boolean estaLavandose;
  private Set<Temperatura> temperaturasAptas;

  public Prenda(Tipo tipo,
                Material material,
                Trama trama,
                Color colorPrimario,
                Color colorSecundario,
                EstadoPrenda estado,
                boolean estaLavandose,
                Set<Temperatura> temperaturasAptas) {
    this.tipo = tipo;
    this.material = material;
    this.trama = trama;
    this.colorPrimario = colorPrimario;
    this.colorSecundario = colorSecundario;
    this.estado = estado;
    this.estaLavandose = estaLavandose;
    this.temperaturasAptas = temperaturasAptas;
  }

  public boolean esDeCategoria(Categoria categoria) {
    return tipo.getCategoria().equals(categoria);
  }

  public boolean esSugerible() {
    return !estaLavandose && estado.esSugerible();
  }

  public boolean esAptaPara(Temperatura temperatura) {
    return temperaturasAptas.contains(temperatura);
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
