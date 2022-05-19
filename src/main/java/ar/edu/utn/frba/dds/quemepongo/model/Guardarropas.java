package ar.edu.utn.frba.dds.quemepongo.model;

import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Guardarropas {
  private ServicioMeteorologico servicioMeteorologico;
  private List<Prenda> prendas;

  public Guardarropas(ServicioMeteorologico servicioMeteorologico,
                      List<Prenda> prendas) {
    this.servicioMeteorologico = servicioMeteorologico;
    this.prendas = prendas;
  }

  public Atuendo sugerir() {
    return new Atuendo(
      sugerirPrenda(Categoria.PARTE_SUPERIOR),
      sugerirPrenda(Categoria.PARTE_INFERIOR),
      sugerirPrenda(Categoria.CALZADO),
      sugerirPrenda(Categoria.ACCESORIO)
    );
  }

  private Prenda sugerirPrenda(Categoria categoria) {
    List<Prenda> prendasPosibles = getPrendasSugeribles(categoria);
    return prendasPosibles.get(ThreadLocalRandom.current()
        .nextInt(prendasPosibles.size()) % prendasPosibles.size()
    );
  }

  private List<Prenda> getPrendasSugeribles(Categoria categoria) {
    return prendas.stream()
        .filter(Prenda::esSugerible)
        .filter(it -> it.esAptaPara(servicioMeteorologico.getTemperatura()))
        .filter(it -> it.esDeCategoria(categoria))
        .collect(Collectors.toList());
  }
}
