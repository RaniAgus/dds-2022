package ar.edu.utn.frba.dds.impactoambiental.controllers;

import static ar.edu.utn.frba.dds.impactoambiental.utils.MapUtil.entry;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.dtos.FilaReporteEvolucionDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.SidebarSector;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorialDto;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorialFactory;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioSectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioTipoDeConsumo;
import com.google.common.collect.ImmutableMap;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;


public class AgenteSectorialController implements Controller {
  RepositorioTipoDeConsumo repoTipoDeConsumo = RepositorioTipoDeConsumo.getInstance();

  public ModelAndView reportesConsumoIndividual(Request request, Response response) {
    UsuarioSectorTerritorial usuario = request.attribute("usuario");
    SectorTerritorial sector = usuario.getSectorTerritorial();
    
    ReporteSectorialDto reporte;
    if(Context.of(request).hasBodyParams()) {
      Integer anio = Form.of(request).getParamOrError("anio", "Es necesario indicar un año")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el anio en el select option")
        .getValor();
      
      Integer mes = Form.of(request).getParamOrError("mes", "Es necesario indicar un mes")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el mes en el select option")
        .getValor();

      Periodicidad periodicidad;  

      if (mes == 0) {
        periodicidad = Periodicidad.ANUAL;
        mes = 1;
      }
      else {
        periodicidad = Periodicidad.MENSUAL;
      }

      LocalDate fecha = LocalDate.of(anio, mes, 1);

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
      "usuario", entry(usuario),
      "sectorTerritorial", entry(sector),
      "reporte", entry(reporte),
      "sidebar", entry(new SidebarSector(false, false, true, false))
    );
    return new ModelAndView(model, "pages/sectoresterritoriales/reportes/consumo/individual.html.hbs");
  }

  public ModelAndView reportesConsumoEvolucion(Request request, Response response) {
    UsuarioSectorTerritorial usuario = request.attribute("usuario");
    SectorTerritorial sector = usuario.getSectorTerritorial();

    ReporteSectorialDto primerReporte;
    ReporteSectorialDto segundoReporte;
    ReporteSectorialDto reporteEvolucion;
    if(Context.of(request).hasBodyParams()) {
      Integer anioInicial = Form.of(request).getParamOrError("anioInicial", "Es necesario indicar un año")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el anio en el select option")
        .getValor();

      Integer anioFinal = Form.of(request).getParamOrError("anioFinal", "Es necesario indicar un año")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el anio en el select option")
        .getValor();
      
      Integer mes = Form.of(request).getParamOrError("mes", "Es necesario indicar un mes")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el mes en el select option")
        .getValor();
      
      Periodicidad periodicidad;

      if (mes == 0) {
        periodicidad = Periodicidad.ANUAL;
        mes = 1;
      } else {
        periodicidad = Periodicidad.MENSUAL;
      }      

      LocalDate primerFecha = LocalDate.of(anioInicial, mes, 1);
      LocalDate segundaFecha = LocalDate.of(anioFinal, mes, 1);

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

    Map<TipoDeConsumo, FilaReporteEvolucionDto> consumos = new HashMap<>();
    primerReporte.getHuellaCarbonoPorTipoDeConsumo().forEach((tipoDeConsumo, d) -> {
      FilaReporteEvolucionDto fila = new FilaReporteEvolucionDto(
        primerReporte.getHuellaCarbonoPorTipoDeConsumo().get(tipoDeConsumo),
        segundoReporte.getHuellaCarbonoPorTipoDeConsumo().get(tipoDeConsumo),
        reporteEvolucion.getHuellaCarbonoPorTipoDeConsumo().get(tipoDeConsumo)
      );
      consumos.put(tipoDeConsumo, fila);
    });

    ImmutableMap<String , Object> model = ImmutableMap.of(
      "usuario", entry(usuario),
      "sectorTerritorial", entry(sector),
      "primerTotal", entry(primerReporte.getHuellaCarbonoTotal()),
      "segundoTotal", entry(segundoReporte.getHuellaCarbonoTotal()),
      "evolucionTotal", entry(reporteEvolucion.getHuellaCarbonoTotal()),
      "consumos", entry(consumos),
      "sidebar", entry(new SidebarSector(false, false, false, true))
    );
    return new ModelAndView(model, "pages/sectoresterritoriales/reportes/consumo/evolucion.html.hbs");
  }


  public ModelAndView reportesOrganizacionIndividual(Request request, Response response) {
    UsuarioSectorTerritorial usuario = request.attribute("usuario");
    SectorTerritorial sector = usuario.getSectorTerritorial();

    ReporteSectorialDto reporte;
    if(Context.of(request).hasBodyParams()) {
      Integer anio = Form.of(request).getParamOrError("anio", "Es necesario indicar un año")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el anio en el select option")
        .getValor();
      
      Integer mes = Form.of(request).getParamOrError("mes", "Es necesario indicar un mes")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el mes en el select option")
        .getValor();

      Periodicidad periodicidad;  

      if (mes == 0) {
        periodicidad = Periodicidad.ANUAL;
        mes = 1;
      }
      else {
        periodicidad = Periodicidad.MENSUAL;
      }

      LocalDate fecha = LocalDate.of(anio, mes, 1);


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
      "usuario", entry(usuario),
      "sectorTerritorial", entry(sector),
      "reporte", entry(reporte),
      "sidebar", entry(new SidebarSector(true, false, false, false))
    );
    return new ModelAndView(model, "pages/sectoresterritoriales/reportes/organizacion/individual.html.hbs");
  }

  public ModelAndView reportesOrganizacionEvolucion(Request request, Response response) {
    UsuarioSectorTerritorial usuario = request.attribute("usuario");
    SectorTerritorial sector = usuario.getSectorTerritorial();

    ReporteSectorialDto primerReporte;
    ReporteSectorialDto segundoReporte;
    ReporteSectorialDto reporteEvolucion;
    if(Context.of(request).hasBodyParams()) {
      Integer anioInicial = Form.of(request).getParamOrError("anioInicial", "Es necesario indicar un año")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el anio en el select option")
        .getValor();

      Integer anioFinal = Form.of(request).getParamOrError("anioFinal", "Es necesario indicar un año")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el anio en el select option")
        .getValor();
      
      Integer mes = Form.of(request).getParamOrError("mes", "Es necesario indicar un mes")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el mes en el select option")
        .getValor();
      
      Periodicidad periodicidad;

      if (mes == 0) {
        periodicidad = Periodicidad.ANUAL;
        mes = 1;
      } else {
        periodicidad = Periodicidad.MENSUAL;
      }      

      LocalDate primerFecha = LocalDate.of(anioInicial, mes, 1);
      LocalDate segundaFecha = LocalDate.of(anioFinal, mes, 1);

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

    Map<TipoDeOrganizacion, FilaReporteEvolucionDto> consumos = new HashMap<>();
    primerReporte.getHuellaCarbonoPorTipoDeOrganizacion().forEach((tipoDeOrganizacion, d) -> {
      FilaReporteEvolucionDto fila = new FilaReporteEvolucionDto(
        primerReporte.getHuellaCarbonoPorTipoDeOrganizacion().get(tipoDeOrganizacion),
        segundoReporte.getHuellaCarbonoPorTipoDeOrganizacion().get(tipoDeOrganizacion),
        reporteEvolucion.getHuellaCarbonoPorTipoDeOrganizacion().get(tipoDeOrganizacion)
      );
      consumos.put(tipoDeOrganizacion, fila);
    });

    ImmutableMap<String , Object> model = ImmutableMap.of(
      "usuario", entry(usuario),
      "sectorTerritorial", entry(sector),
      "primerTotal", entry(primerReporte.getHuellaCarbonoTotal()),
      "segundoTotal", entry(segundoReporte.getHuellaCarbonoTotal()),
      "evolucionTotal", entry(reporteEvolucion.getHuellaCarbonoTotal()),
      "consumos", entry(consumos),
      "sidebar", entry(new SidebarSector(false, true, false, false))
    );
    return new ModelAndView(model, "pages/sectoresterritoriales/reportes/organizacion/evolucion.html.hbs");
  }
}
