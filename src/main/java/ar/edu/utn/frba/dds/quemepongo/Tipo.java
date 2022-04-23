package ar.edu.utn.frba.dds.quemepongo;


public class Tipo {
  private String nombre;
  private Categoria categoria;

  public Tipo(String nombre, Categoria categoria) {
    this.nombre = nombre;
    this.categoria = categoria;
  }

  public Categoria getCategoria() {
    return categoria;
  }
}
