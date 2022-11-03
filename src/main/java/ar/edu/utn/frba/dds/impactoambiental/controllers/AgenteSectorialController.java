package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorialDto;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorialFactory;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioTipoDeConsumo;
import com.google.common.collect.ImmutableMap;
import java.time.LocalDate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AgenteSectorialController implements Controller {
  RepositorioTipoDeConsumo repoTipoDeConsumo = RepositorioTipoDeConsumo.getInstance();

  public ModelAndView reportesConsumoIndividual(Request request, Response response) {
    SectorTerritorial sector = request.session().attribute("sector");
    
    ReporteSectorialDto reporte;
    if(Context.of(request).hasBodyParams()) {
      Periodicidad periodicidad = Form.of(request).getParamOrError("periodicidad", "Es necesario indicar una periodicidad")
        .apply(s -> Periodicidad.valueOf(s.toUpperCase()), "La periodicidad debe ser anual o mensual")
        .getValor();
      LocalDate fecha = Form.of(request).getParamOrError("fecha", "Es necesario indicar una fecha")
        .apply(LocalDate::parse, "La fecha debe tener el formato yyyy-MM-dd")
        .getValor();

      reporte = new ReporteSectorialFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(fecha, periodicidad),
        sector
      ).getReporte();
    }
    else { //Re rancio esto porque el total y el periodo son null, la idea es que en la vista no se muestre ni el total
      reporte = ReporteSectorialDto.reporteVacio();
    }

    ImmutableMap<String , Object> model = ImmutableMap.of(
      "sectorTerritorial", sector,
      "reporte", reporte
    );
    return new ModelAndView(model, "pages/sectoresterritoriales/reportes/consumo/individual.html.hbs");
  }

  public ModelAndView reportesConsumoEvolucion(Request request, Response response) {
    SectorTerritorial sector = request.session().attribute("sector");
    
    ReporteSectorialDto primerReporte;
    ReporteSectorialDto segundoReporte;
    ReporteSectorialDto reporteEvolucion;
    if(Context.of(request).hasBodyParams()) {
      Periodicidad periodicidad = Form.of(request).getParamOrError("periodicidad", "Es necesario indicar una periodicidad")
        .apply(s -> Periodicidad.valueOf(s.toUpperCase()), "La periodicidad debe ser anual o mensual")
        .getValor();
      LocalDate primerFecha = Form.of(request).getParamOrError("fecha", "Es necesario indicar una fecha")
        .apply(LocalDate::parse, "La fecha debe tener el formato yyyy-MM-dd")
        .getValor();
      LocalDate segundaFecha = Form.of(request).getParamOrError("fecha2", "Es necesario indicar una fecha")
        .apply(LocalDate::parse, "La fecha debe tener el formato yyyy-MM-dd")
        .getValor();

      primerReporte = new ReporteSectorialFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(primerFecha, periodicidad),
        sector
      ).getReporte();

      segundoReporte = new ReporteSectorialFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(segundaFecha, periodicidad),
        sector
      ).getReporte();

      reporteEvolucion = segundoReporte.compararCon(primerReporte);
    }
    else { //Re rancio esto porque el total y el periodo son null, la idea es que en la vista no se muestre ni el total
      primerReporte = ReporteSectorialDto.reporteVacio();
      segundoReporte = ReporteSectorialDto.reporteVacio();
      reporteEvolucion = ReporteSectorialDto.reporteVacio();
    }

    ImmutableMap<String , Object> model = ImmutableMap.of(
      "sectorTerritorial", sector,
      "primerReporte", primerReporte,
      "segundoReporte", segundoReporte,
      "reporteEvolucion", reporteEvolucion
    );
    return new ModelAndView(model, "pages/sectoresterritoriales/reportes/consumo/index.html.hbs");
  }


  public ModelAndView reportesOrganizacionIndividual(Request request, Response response) {
    SectorTerritorial sector = request.session().attribute("sector");
    
    ReporteSectorialDto reporte;
    if(Context.of(request).hasBodyParams()) {
      Periodicidad periodicidad = Form.of(request).getParamOrError("periodicidad", "Es necesario indicar una periodicidad")
        .apply(s -> Periodicidad.valueOf(s.toUpperCase()), "La periodicidad debe ser anual o mensual")
        .getValor();
      LocalDate fecha = Form.of(request).getParamOrError("fecha", "Es necesario indicar una fecha")
        .apply(LocalDate::parse, "La fecha debe tener el formato yyyy-MM-dd")
        .getValor();

      reporte = new ReporteSectorialFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(fecha, periodicidad),
        sector
      ).getReporte();
    }
    else { //Re rancio esto porque el total y el periodo son null, la idea es que en la vista no se muestre ni el total
      reporte = ReporteSectorialDto.reporteVacio();
    }

    ImmutableMap<String , Object> model = ImmutableMap.of(
      "sectorTerritorial", sector,
      "reporte", reporte
    );
    return new ModelAndView(model, "pages/sectoresterritoriales/reportes/organizacion/individual.html.hbs");
  }

  public ModelAndView reportesOrganizacionEvolucion(Request request, Response response) {
    SectorTerritorial sector = request.session().attribute("sector");
    
    ReporteSectorialDto primerReporte;
    ReporteSectorialDto segundoReporte;
    ReporteSectorialDto reporteEvolucion;
    if(Context.of(request).hasBodyParams()) {
      Periodicidad periodicidad = Form.of(request).getParamOrError("periodicidad", "Es necesario indicar una periodicidad")
        .apply(s -> Periodicidad.valueOf(s.toUpperCase()), "La periodicidad debe ser anual o mensual")
        .getValor();
      LocalDate primerFecha = Form.of(request).getParamOrError("fecha", "Es necesario indicar una fecha")
        .apply(LocalDate::parse, "La fecha debe tener el formato yyyy-MM-dd")
        .getValor();
      LocalDate segundaFecha = Form.of(request).getParamOrError("fecha2", "Es necesario indicar una fecha")
        .apply(LocalDate::parse, "La fecha debe tener el formato yyyy-MM-dd")
        .getValor();

      primerReporte = new ReporteSectorialFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(primerFecha, periodicidad),
        sector
      ).getReporte();

      segundoReporte = new ReporteSectorialFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(segundaFecha, periodicidad),
        sector
      ).getReporte();

      reporteEvolucion = segundoReporte.compararCon(primerReporte);
    }
    else { //Re rancio esto porque el total y el periodo son null, la idea es que en la vista no se muestre ni el total
      primerReporte = ReporteSectorialDto.reporteVacio();
      segundoReporte = ReporteSectorialDto.reporteVacio();
      reporteEvolucion = ReporteSectorialDto.reporteVacio();
    }

    ImmutableMap<String , Object> model = ImmutableMap.of(
      "sectorTerritorial", sector,
      "primerReporte", primerReporte,
      "segundoReporte", segundoReporte,
      "reporteEvolucion", reporteEvolucion
    );
    return new ModelAndView(model, "pages/sectoresterritoriales/reportes/organizacion/index.html.hbs");
  }
}
