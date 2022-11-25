package ar.edu.utn.frba.dds.impactoambiental.controllers;

import static ar.edu.utn.frba.dds.impactoambiental.utils.MapUtil.entry;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.dtos.FilaReporteEvolucionDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.SidebarOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.da.DatoActividad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.DatosActividadesParser;
import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeBytes;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteOrganizacionalDto;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteOrganizacionalFactory;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioOrganizaciones;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioTipoDeConsumo;
import com.google.common.collect.ImmutableMap;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class OrganizacionController implements Controller {
  RepositorioTipoDeConsumo repoTipoDeConsumo = RepositorioTipoDeConsumo.getInstance();
  RepositorioOrganizaciones repoOrganizaciones = RepositorioOrganizaciones.getInstance();

  private UsuarioOrganizacion organizacionDeSesion(Request req) {
    return req.attribute("usuario");
  }

  public ModelAndView vinculaciones(Request request, Response response) {
    UsuarioOrganizacion usuario = organizacionDeSesion(request);
    Organizacion org = usuario.getOrganizacion();
    List<Sector> sectores = org.getSectores();
    List<VinculacionDto> vinculaciones = sectores.stream().flatMap(sector -> sector.getVinculacionesPendientes().stream()
      .map(vinculacion -> new VinculacionDto(
        vinculacion.getId(), 
        vinculacion.getMiembro(), 
        org, 
        sector, 
        vinculacion.getEstado()
      ))
    ).collect(Collectors.toList());

    ImmutableMap<String, Object> model = ImmutableMap.of(
        "usuario", usuario,
        "organizacion", org,
        "vinculaciones", vinculaciones,
        "sidebar", new SidebarOrganizacion(true, false, false, false)
    );

    return new ModelAndView(model, "pages/organizaciones/vinculaciones/index.html.hbs");
  }

  public ModelAndView aceptarVinculacion(Request request, Response response) {
    UsuarioOrganizacion usuarioOrg = organizacionDeSesion(request);
    Long idVinculacion = Form.of(request).getParamOrError("vinculacionId", "Es necesario indicar un id de vinculación")
      .apply(Long::parseLong, "El id de vinculación debe ser numérico")
      .getValor();

    //TODO: Validar (aplica para todo el sistema)

    Vinculacion vinc = usuarioOrg.getOrganizacion().getSectores().stream()
      .flatMap(sector -> sector.getVinculacionesPendientes().stream())
      .filter(vinculacion -> vinculacion.getId().equals(idVinculacion))
      .findFirst().get(); // TODO: Validar esto tambien.

    withTransaction(() -> {        
      vinc.aceptar();
      entityManager().merge(vinc);
    });

    response.redirect(usuarioOrg.getHomeUrl());
    return null;
  }

  public ModelAndView da(Request request, Response response) {
    UsuarioOrganizacion usuarioOrg = organizacionDeSesion(request);

    ImmutableMap<String, Object> model = ImmutableMap.of(
      "usuario", request.attribute("usuario"),
      "organizacion", usuarioOrg.getOrganizacion(),
      "tiposDeConsumo", repoTipoDeConsumo.obtenerTodos(),
      "periodicidades", Arrays.asList(Periodicidad.values()),
      "sidebar", new SidebarOrganizacion(false, true, false, false)
    );
    return new ModelAndView(model, "pages/organizaciones/da/index.html.hbs");
  }

  public ModelAndView daManual(Request request, Response response) {
    UsuarioOrganizacion usuarioOrg = organizacionDeSesion(request);

    ImmutableMap<String, Object> model = ImmutableMap.of(
      "organizacion", usuarioOrg.getOrganizacion(),
      "tiposDeConsumo", repoTipoDeConsumo.obtenerTodos(),
      "periodicidades", Arrays.asList(Periodicidad.values()),
      "sidebar", new SidebarOrganizacion(false, true, false, false)
    );
    return new ModelAndView(model, "pages/organizaciones/da/manual.html.hbs");
  }

  public ModelAndView cargarDA(Request request, Response response) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();

    final List<DatoActividad> finalDAs = daDesdeCSV(request);
    
    withTransaction(() -> {
      organizacion.agregarDatosActividad(finalDAs);
      repoOrganizaciones.actualizar(organizacion);
    });

    response.redirect("/organizaciones/me/da");
    return null;
  }

  public ModelAndView cargarDAManual (Request request, Response response) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();
    
    final List<DatoActividad> finalDAs = daDesdeQueryParams(request);

    withTransaction(() -> {
      organizacion.agregarDatosActividad(finalDAs);
      repoOrganizaciones.actualizar(organizacion);
    });

    response.redirect("/organizaciones/me/da/manual");
    return null;
  }

  public ModelAndView reportesIndividual(Request request, Response response) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();

    ReporteOrganizacionalDto reporte;
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

      reporte = new ReporteOrganizacionalFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(fecha, periodicidad),
        organizacion
      ).getReporte();
    }
    else { //Re rancio esto porque el total y el periodo son null, la idea es que en la vista no se muestre ni el total
      reporte = ReporteOrganizacionalDto.reporteVacio();
    }

    ImmutableMap<String , Object> model = ImmutableMap.of(
      "usuario", entry(organizacionDeSesion(request)),
      "organizacion", entry(organizacion),
      "reporte", entry(reporte),
      "anios", entry(organizacion.aniosConsumo()),
      "sidebar", new SidebarOrganizacion(false, false, false, true)
    );
    return new ModelAndView(model, "pages/organizaciones/reportes/individual.html.hbs");
  }

  public ModelAndView reportesEvolucion(Request request, Response response) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();

    ReporteOrganizacionalDto primerReporte;
    ReporteOrganizacionalDto segundoReporte;
    ReporteOrganizacionalDto reporteEvolucion;
    if(Context.of(request).hasBodyParams()) {
      Integer anioInicial = Form.of(request).getParamOrError("anioInicial", "Es necesario indicar un año")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el anio en el select option")
        .getValor();

      Integer anioFinal = Form.of(request).getParamOrError("anioFinal", "Es necesario indicar un año")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el anio en el select option")
        .getValor();
      
      Integer mes1 = Form.of(request).getParamOrError("mes1", "Es necesario indicar un mes")
        .apply(Integer::parseInt, "Somos unos forros que escribimos mal el mes en el select option")
        .getValor();

      Integer mes2 = Form.of(request).getParamOrError("mes2", "Es necesario indicar un mes")
          .apply(Integer::parseInt, "Somos unos forros que escribimos mal el mes en el select option")
          .getValor();
      
      Periodicidad periodicidad1;
      Periodicidad periodicidad2;

      if (mes1 == 0) {
        periodicidad1 = Periodicidad.ANUAL;
        mes1 = 1;
      } else {
        periodicidad1 = Periodicidad.MENSUAL;
      }

      if (mes2 == 0) {
        periodicidad2 = Periodicidad.ANUAL;
        mes2 = 1;
      } else {
        periodicidad2 = Periodicidad.MENSUAL;
      }

      LocalDate primerFecha = LocalDate.of(anioInicial, mes1, 1);
      LocalDate segundaFecha = LocalDate.of(anioFinal, mes2, 1);

      primerReporte = new ReporteOrganizacionalFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(primerFecha, periodicidad1),
        organizacion
      ).getReporte();

      segundoReporte = new ReporteOrganizacionalFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(segundaFecha, periodicidad2),
        organizacion
      ).getReporte();

      reporteEvolucion = segundoReporte.compararCon(primerReporte);
    }
    else { //Re rancio esto porque el total y el periodo son null, la idea es que en la vista no se muestre ni el total
      primerReporte = ReporteOrganizacionalDto.reporteVacio();
      segundoReporte = ReporteOrganizacionalDto.reporteVacio();
      reporteEvolucion = ReporteOrganizacionalDto.reporteVacio();
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
      "usuario", entry(organizacionDeSesion(request)),
      "organizacion", entry(organizacion),
      "primerTotal", entry(primerReporte.getHuellaCarbonoTotal()),
      "segundoTotal", entry(segundoReporte.getHuellaCarbonoTotal()),
      "evolucionTotal", entry(reporteEvolucion.getHuellaCarbonoTotal()),
      "consumos", consumos,
      "anios", entry(organizacion.aniosConsumo()),
      "sidebar", new SidebarOrganizacion(false, false, true, false)
    );

    return new ModelAndView(model, "pages/organizaciones/reportes/evolucion.html.hbs");
  }

  private List<DatoActividad> daDesdeCSV(Request request) {
    return Form.of(request).getFile("archivo")
        .map(bytes -> new DatosActividadesParser(
            repoTipoDeConsumo,
            new LectorDeBytes(bytes),
            1, ';', "MM/yyyy")
            .getDatosActividad())
        .orElse(Collections.emptyList());
  }

  private List<DatoActividad> daDesdeQueryParams(Request request) {
    String DAComoLineaCSV = String.join(";",
      request.queryParams("tipoDeConsumo"),
      request.queryParams("cantidadConsumo"),
      request.queryParams("periodicidad"),
      request.queryParams("fechaInicial").substring(0, 7).replace("-", "/")
    );

    DatosActividadesParser DAParser = new DatosActividadesParser(
      repoTipoDeConsumo, 
      new LectorDeBytes(DAComoLineaCSV.getBytes()), 0, ';', "yyyy/MM");
    return DAParser.getDatosActividad();
  }

}
