package ar.edu.utn.frba.dds.quemepongo.model.prenda;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Temperatura;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.estado.EstadoPrenda;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.estado.Nueva;
import ar.edu.utn.frba.dds.quemepongo.exception.PrendaInvalidaException;

import java.awt.*;
import java.util.Set;

public class Borrador {
  private Tipo tipo;
  private Material material;
  private Trama trama = Trama.LISA;
  private Color colorPrimario;
  private Color colorSecundario;
  private EstadoPrenda estado = new Nueva();
  private boolean estaLavandose = false;
  private Set<Temperatura> temperaturasAptas;

  public Borrador conTipo(Tipo tipo) {
    this.tipo = tipo;
    return this;
  }

  public Borrador conMaterial(Material material) {
    this.material = material;
    return this;
  }

  public Borrador conTrama(Trama trama) {
    this.trama = trama;
    return this;
  }

  public Borrador conColorPrimario(Color colorPrimario) {
    this.colorPrimario = colorPrimario;
    return this;
  }

  public Borrador conColorSecundario(Color colorSecundario) {
    this.colorSecundario = colorSecundario;
    return this;
  }

  public Borrador conEstado(EstadoPrenda estado) {
    this.estado = estado;
    return this;
  }

  public Borrador setEstaLavandose(boolean estaLavandose) {
    this.estaLavandose = estaLavandose;
    return this;
  }

  public Borrador conTemperaturasAptas(Set<Temperatura> temperaturasAptas) {
    this.temperaturasAptas = temperaturasAptas;
    return this;
  }

  public Prenda crearPrenda() {
    return new Prenda(
        validarParametroNoNulo(tipo, "tipo"),
        validarMaterial(material, tipo),
        validarParametroNoNulo(trama, "trama"),
        validarParametroNoNulo(colorPrimario, "color primario"),
        colorSecundario,
        validarParametroNoNulo(estado, "estado"),
        estaLavandose,
        validarParametroNoNulo(temperaturasAptas, "temperaturas aptas")
    );
  }

  private static <T> T validarParametroNoNulo(T parametro, String nombre) {
    if (parametro == null) {
      throw new PrendaInvalidaException(nombre);
    }
    return parametro;
  }

  private static Material validarMaterial(Material material, Tipo tipo) {
    validarParametroNoNulo(material, "material");
    if (!tipo.esMaterialValido(material)) {
      throw new PrendaInvalidaException(tipo, material);
    }
    return material;
  }

}
