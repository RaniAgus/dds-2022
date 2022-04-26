package ar.edu.utn.frba.dds.quemepongo;

import java.awt.*;

public class Borrador {
  private Tipo tipo;
  private Material material;
  private Trama trama;
  private Color colorPrimario;
  private Color colorSecundario;

  public Borrador(Tipo tipo) {
    if (tipo == null) {
      throw new PrendaInvalidaException("Falta parámetro: tipo");
    }
    this.tipo = tipo;
  }

  public Borrador conMaterial(Material material) {
    validarParametroNoNulo(material, "material");
    if (!tipo.esMaterialValido(material)) {
      throw new PrendaInvalidaException(tipo, material);
    }
    this.material = material;
    return this;
  }

  public Borrador conTrama(Trama trama) {
    this.trama = trama;
    return this;
  }

  public Borrador conColorPrimario(Color colorPrimario) {
    validarParametroNoNulo(colorPrimario, "color primario");
    this.colorPrimario = colorPrimario;
    return this;
  }

  public Borrador conColorSecundario(Color colorSecundario) {
    this.colorSecundario = colorSecundario;
    return this;
  }

  public Prenda crearPrenda() {
    validarParametroNoNulo(material, "material");
    validarParametroNoNulo(colorPrimario, "color primario");

    return new Prenda(
        tipo,
        material,
        trama != null ? trama : Trama.LISA,
        colorPrimario,
        colorSecundario
    );
  }

  private void validarParametroNoNulo(Object parametro, String nombre) {
    if (parametro == null) {
      throw new PrendaInvalidaException(nombre);
    }
  }
}
