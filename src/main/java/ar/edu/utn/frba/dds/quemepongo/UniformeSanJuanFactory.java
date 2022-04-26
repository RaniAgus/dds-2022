package ar.edu.utn.frba.dds.quemepongo;

import java.awt.*;

public class UniformeSanJuanFactory extends UniformeFactory {
  private final Borrador borradorSuperior = new Borrador(Tipo.CHOMBA)
      .conMaterial(Material.PIQUE)
      .conColorPrimario(Color.GREEN);

  private final Borrador borradorInferior = new Borrador(Tipo.PANTALON)
      .conMaterial(Material.ACETATO)
      .conColorPrimario(Color.GRAY);

  private final Borrador borradorCalzado = new Borrador(Tipo.ZAPATILLAS)
      .conMaterial(Material.TELA)
      .conColorPrimario(Color.WHITE);

  @Override
  public Borrador getBorradorSuperior() {
    return borradorSuperior;
  }

  @Override
  public Borrador getBorradorInferior() {
    return borradorInferior;
  }

  @Override
  public Borrador getBorradorCalzado() {
    return borradorCalzado;
  }
}
