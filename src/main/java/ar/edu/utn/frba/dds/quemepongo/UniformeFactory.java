package ar.edu.utn.frba.dds.quemepongo;

public abstract class UniformeFactory {
  public Uniforme crearUniforme() {
    return new Uniforme(
        getBorradorSuperior().crearPrenda(),
        getBorradorInferior().crearPrenda(),
        getBorradorCalzado().crearPrenda()
    );
  }

  protected abstract Borrador getBorradorSuperior();
  protected abstract Borrador getBorradorInferior();
  protected abstract Borrador getBorradorCalzado();
}
