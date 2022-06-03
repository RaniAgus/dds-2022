package models.da;

import com.opencsv.bean.*;

public class DatoActividad {

  public DatoActividad() {
    /* Builder Publico Vacio Para cumplir con bean de OpenCSV */
  }

  public TipoDeConsumo getTipo() {
    return tipo;
  }

  @CsvCustomBindByPosition(position = 0,converter = ConvertToTipoDeConsumo.class)
  private TipoDeConsumo tipo;

  @CsvBindByPosition(position = 1)
  private Double valor;

  @CsvBindByPosition(position = 2)
  private Periodicidad periodicidad;

  @CsvBindByPosition(position = 3)
  private String periodo;
}