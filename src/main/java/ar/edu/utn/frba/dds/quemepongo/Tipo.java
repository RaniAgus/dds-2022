package ar.edu.utn.frba.dds.quemepongo;


import java.util.Arrays;
import java.util.List;

public enum Tipo {
  ANTEOJOS(
      Categoria.ACCESORIO,
      Arrays.asList(Material.PLASTICO, Material.METAL)
  ),
  CAMPERA(
      Categoria.PARTE_SUPERIOR,
      Arrays.asList(Material.CUERO, Material.JEAN, Material.LANA)
  ),
  PANTALON(
      Categoria.PARTE_INFERIOR,
      Arrays.asList(Material.CUERO, Material.JEAN)
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
