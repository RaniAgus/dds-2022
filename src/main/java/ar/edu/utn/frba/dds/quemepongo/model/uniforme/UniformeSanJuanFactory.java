package ar.edu.utn.frba.dds.quemepongo.model.uniforme;

import ar.edu.utn.frba.dds.quemepongo.model.Borrador;
import ar.edu.utn.frba.dds.quemepongo.model.Material;
import ar.edu.utn.frba.dds.quemepongo.model.Tipo;

import java.awt.*;

public class UniformeSanJuanFactory extends UniformeFactory {
  private final Borrador borradorSuperior = new Borrador()
      .conTipo(Tipo.CHOMBA)
      .conMaterial(Material.PIQUE)
      .conColorPrimario(Color.GREEN);

  private final Borrador borradorInferior = new Borrador()
      .conTipo(Tipo.PANTALON)
      .conMaterial(Material.ACETATO)
      .conColorPrimario(Color.GRAY);

  private final Borrador borradorCalzado = new Borrador()
      .conTipo(Tipo.ZAPATILLAS)
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
