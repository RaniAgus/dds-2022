package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.da.DatoActividad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.DatosActividadesParser;
import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        "vinculaciones", vinculaciones
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
      "organizacion", usuarioOrg.getOrganizacion(),
      "tiposDeConsumo", repoTipoDeConsumo.obtenerTodos(),
        "periodicidades", Arrays.asList(Periodicidad.values())
    );
    return new ModelAndView(model, "pages/organizaciones/da/index.html.hbs");
  }

  public ModelAndView cargarDA(Request request, Response response) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();

    List<DatoActividad> DAs;

    if (request.contentType().startsWith("multipart/form-data")) {
      DAs = daDesdeCSV(request);
    } else {
      DAs = daDesdeQueryParams(request);
    }

    final List<DatoActividad> finalDAs = DAs;
    
    withTransaction(() -> {
      organizacion.agregarDatosActividad(finalDAs);
      repoOrganizaciones.actualizar(organizacion);
    });

    response.redirect("/organizaciones/me/da");
    return null;
  }

  public ModelAndView reportesIndividual(Request request, Response response) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();

    ReporteOrganizacionalDto reporte;
    if(Context.of(request).hasBodyParams()) {
      Periodicidad periodicidad = Form.of(request).getParamOrError("periodicidad", "Es necesario indicar una periodicidad")
        .apply(s -> Periodicidad.valueOf(s.toUpperCase()), "La periodicidad debe ser anual o mensual")
        .getValor();
      LocalDate fecha = Form.of(request).getParamOrError("fecha", "Es necesario indicar una fecha")
        .apply(LocalDate::parse, "La fecha debe tener el formato yyyy-MM-dd")
        .getValor();

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
      "organizacion", organizacion,
      "reporte", reporte
    );
    return new ModelAndView(model, "pages/organizaciones/reportes/individual.html.hbs");
  }

  public ModelAndView reportesEvolucion(Request request, Response response) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();

    ReporteOrganizacionalDto primerReporte;
    ReporteOrganizacionalDto segundoReporte;
    ReporteOrganizacionalDto reporteEvolucion;
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

      primerReporte = new ReporteOrganizacionalFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(primerFecha, periodicidad),
        organizacion
      ).getReporte();

      segundoReporte = new ReporteOrganizacionalFactory(
        repoTipoDeConsumo.obtenerTodos(),
        new Periodo(segundaFecha, periodicidad),
        organizacion
      ).getReporte();

      reporteEvolucion = segundoReporte.compararCon(primerReporte);
    }
    else { //Re rancio esto porque el total y el periodo son null, la idea es que en la vista no se muestre ni el total
      primerReporte = ReporteOrganizacionalDto.reporteVacio();
      segundoReporte = ReporteOrganizacionalDto.reporteVacio();
      reporteEvolucion = ReporteOrganizacionalDto.reporteVacio();
    }

    ImmutableMap<String , Object> model = ImmutableMap.of(
      "organizacion", organizacion,
      "primerReporte", primerReporte,
      "segundoReporte", segundoReporte,
      "reporteEvolucion", reporteEvolucion
    );
    return new ModelAndView(model, "pages/organizaciones/reportes/evolucion.html.hbs");
  }

  private List<DatoActividad> daDesdeCSV(Request request) {
    return Form.of(request).getFile("archivo")
        .map(bytes -> new DatosActividadesParser(
            repoTipoDeConsumo,
            new LectorDeArchivos(bytes),
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
      new LectorDeArchivos(DAComoLineaCSV.getBytes()), 0, ';', "yyyy/MM");
    return DAParser.getDatosActividad();
  }

}
