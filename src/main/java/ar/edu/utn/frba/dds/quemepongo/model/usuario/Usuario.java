package ar.edu.utn.frba.dds.quemepongo.model.usuario;

import ar.edu.utn.frba.dds.quemepongo.model.accion.Accion;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Clima;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Temperatura;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Categoria;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Prenda;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Usuario {
  private String email;
  private List<Guardarropa> guardarropas;
  private List<Accion> acciones;
  private List<Atuendo> sugerencias;

  public Usuario(String email,
                 List<Guardarropa> guardarropas,
                 List<Accion> acciones,
                 List<Atuendo> sugerencias) {
    this.email = email;
    this.guardarropas = guardarropas;
    this.acciones = acciones;
    this.sugerencias = sugerencias;
  }

  public String getEmail() {
    return email;
  }

  public void agregar(Guardarropa guardarropa) {
    guardarropas.add(guardarropa);
  }

  public void emitirAlerta(Clima clima) {
    acciones.forEach(accion -> accion.emitirA(this, clima));
  }

  public void generarSugerencia(Temperatura temperatura) {
    sugerencias.add(new Atuendo(
        sugerirPrendaAleatoria(Categoria.PARTE_SUPERIOR, temperatura),
        sugerirPrendaAleatoria(Categoria.PARTE_INFERIOR, temperatura),
        sugerirPrendaAleatoria(Categoria.CALZADO, temperatura),
        sugerirPrendaAleatoria(Categoria.ACCESORIO, temperatura)
    ));
  }

  private Prenda sugerirPrendaAleatoria(Categoria categoria, Temperatura temperatura) {
    List<Prenda> prendasPosibles = guardarropas.stream()
        .flatMap(it -> it.getPrendasSugeribles(categoria, temperatura))
        .collect(Collectors.toList());

    return prendasPosibles.get(
        ThreadLocalRandom.current().nextInt(prendasPosibles.size())
            % prendasPosibles.size()
    );
  }
}
