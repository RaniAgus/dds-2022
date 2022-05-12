package ar.edu.utn.frba.dds.quemepongo.model;

public class Atuendo {
  private Prenda prendaSuperior;
  private Prenda prendaInferior;
  private Prenda calzado;
  private Prenda accesorio;

  public Atuendo(Prenda prendaSuperior,
                 Prenda prendaInferior,
                 Prenda calzado,
                 Prenda accesorio) {
    this.prendaSuperior = prendaSuperior;
    this.prendaInferior = prendaInferior;
    this.calzado = calzado;
    this.accesorio = accesorio;
  }
}
