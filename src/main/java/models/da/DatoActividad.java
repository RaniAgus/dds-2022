package models.da;

import com.opencsv.bean.CsvBindByName;

public class DatoActividad {
  @CsvBindByName(column = "Tipo de consumo", required = true)
  private TipoDeConsumo tipo;

  @CsvBindByName(column = "Valor", required = true)
  private Double valor;

  @CsvBindByName(column = "Periodicidad", required = true)
  private Periodicidad periodicidad;

  @CsvBindByName(column = "Periodo de imputación", required = true)
  private String periodo;
}