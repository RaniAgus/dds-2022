package ar.edu.utn.frba.dds;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.valueOf;

public class VentaConTarjeta extends Venta {
  private Integer cuotas;
  private BigDecimal coeficientePorCuota;
  private BigDecimal porcentajePorPrenda;

  public VentaConTarjeta(LocalDate fecha,
                         List<PrendaVendida> prendasVendidas,
                         Integer cuotas,
                         BigDecimal coeficientePorCuota,
                         BigDecimal porcentajePorPrenda) {
    super(fecha, prendasVendidas);
    this.cuotas = cuotas;
    this.coeficientePorCuota = coeficientePorCuota;
    this.porcentajePorPrenda = porcentajePorPrenda;
  }

  @Override
  public BigDecimal aplicarRecargo() {
    return coeficientePorCuota
        .multiply(valueOf(cuotas))
        .add(sumaPreciosTotales().multiply(porcentajePorPrenda));
  }
}
