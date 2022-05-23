package ar.edu.utn.frba.dds.quemepongo.model.uniforme;

import ar.edu.utn.frba.dds.quemepongo.model.prenda.Borrador;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Material;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Temperatura;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Tipo;
import com.google.common.collect.ImmutableSet;

import java.awt.Color;

public class UniformeSanJuanFactory extends UniformeFactory {
  private final Borrador borradorSuperior = new Borrador()
      .conTipo(Tipo.CHOMBA)
      .conMaterial(Material.PIQUE)
      .conColorPrimario(Color.GREEN)
      .conTemperaturasAptas(ImmutableSet.of(Temperatura.CALIDO));

  private final Borrador borradorInferior = new Borrador()
      .conTipo(Tipo.PANTALON)
      .conMaterial(Material.ACETATO)
      .conColorPrimario(Color.GRAY)
      .conTemperaturasAptas(ImmutableSet.of(
          Temperatura.CALIDO,
          Temperatura.TEMPLADO
      ));

  private final Borrador borradorCalzado = new Borrador()
      .conTipo(Tipo.ZAPATILLAS)
      .conMaterial(Material.TELA)
      .conColorPrimario(Color.WHITE)
      .conTemperaturasAptas(ImmutableSet.of(
          Temperatura.CALIDO,
          Temperatura.TEMPLADO,
          Temperatura.FRIO
      ));

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
