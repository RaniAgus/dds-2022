package ar.edu.utn.frba.dds.quemepongo.model.prenda;


import java.util.Arrays;
import java.util.List;

public enum Tipo {
  ZAPATILLAS(
      Categoria.CALZADO,
      Arrays.asList(Material.CUERO, Material.TELA)
  ),
  CHOMBA(
      Categoria.PARTE_SUPERIOR,
      Arrays.asList(Material.CUERO, Material.JEAN, Material.LANA, Material.PIQUE)
  ),
  PANTALON(
      Categoria.PARTE_INFERIOR,
      Arrays.asList(Material.CUERO, Material.JEAN, Material.ACETATO)
  ),
  /* ... */;

  private final Categoria categoria;
  private final List<Material> materialesValidos;

  Tipo(Categoria categoria, List<Material> materialesValidos) {
    this.categoria = categoria;
    this.materialesValidos = materialesValidos;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public boolean esMaterialValido(Material material) {
    return materialesValidos.contains(material);
  }
}
