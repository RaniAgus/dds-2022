package ar.edu.utn.frba.dds.quemepongo;


public enum Tipo {
  ANTEOJOS(Categoria.ACCESORIO),
  ZAPATOS(Categoria.CALZADO),
  /* ... */;

  private final Categoria categoria;

  Tipo(Categoria categoria) {
    this.categoria = categoria;
  }

  public Categoria getCategoria() {
    return categoria;
  }
}
