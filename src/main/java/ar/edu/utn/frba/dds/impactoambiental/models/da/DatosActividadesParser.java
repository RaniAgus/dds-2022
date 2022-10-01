package ar.edu.utn.frba.dds.impactoambiental.models.da;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Un ImportadorDeDatosDeActividad que use este parser y el lector de archivos
public class DatosActividadesParser {
  private final RepositorioTipoDeConsumo repositorioTipoDeConsumo;
  private final Character separator;
  private final Integer skiplines;
  private final LectorDeArchivos lectorDeArchivos;

  public DatosActividadesParser(RepositorioTipoDeConsumo repositorioTipoDeConsumo,
                                LectorDeArchivos loader,
                                Integer skiplines,
                                Character separator) {
    this.repositorioTipoDeConsumo = repositorioTipoDeConsumo;
    this.lectorDeArchivos = loader;
    this.skiplines = skiplines;
    this.separator = separator;
  }

  private TipoDeConsumo getTipoDeConsumo(String nombre) {
    return repositorioTipoDeConsumo.buscarPorNombre(nombre)
        .orElseThrow(() -> new IllegalArgumentException("El tipo de consumo '" + nombre + "' no es válido."));
  }

  private DatoActividad parseLine(String line) {
    List<String> campos = Arrays.asList(line.split(separator.toString()));
    if (campos.size() != 4) {
      throw new IllegalArgumentException("La linea no tiene el numero adecuado de campos.");
    }
    TipoDeConsumo tipoDeConsumo = getTipoDeConsumo(campos.get(0));
    Double valor = Double.parseDouble(campos.get(1));
    Periodicidad periodicidad = Periodicidad.valueOf(campos.get(2));

    LocalDate inicioPeriodo;
    String periodo = campos.get(3);

    if (periodicidad == Periodicidad.ANUAL) {
      inicioPeriodo = LocalDate.of(Integer.parseInt(periodo), 1, 1);
    } else {
      String[] partesFecha = periodo.split("/");
      inicioPeriodo = LocalDate.of(Integer.parseInt(partesFecha[1]), Integer.parseInt(partesFecha[0]), 1);
    }

    return new DatoActividad(tipoDeConsumo, valor, new Periodo(inicioPeriodo, periodicidad));
  }

  public List<DatoActividad> getDatosActividad() {
    List<String> lineas = lectorDeArchivos.getLineas();
    lineas = lineas.subList(skiplines, lineas.size());

    return lineas.stream().map(this::parseLine).collect(Collectors.toList());
  }
}
