package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.da.DatoActividad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.DatosActividadesParser;
import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioOrganizaciones;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioTipoDeConsumo;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class OrganizacionController implements Controller {
  RepositorioTipoDeConsumo repoTipoDeConsumo = RepositorioTipoDeConsumo.getInstance();
  RepositorioOrganizaciones repoOrganizaciones = RepositorioOrganizaciones.getInstance();

  private UsuarioOrganizacion organizacionDeSesion(Request req) {
    return req.session().<UsuarioOrganizacion>attribute("usuario");
  }

  public ModelAndView vinculaciones(Request request, Response response) {
    Organizacion org = organizacionDeSesion(request).getOrganizacion();
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
      "organizacion", org,
      "vinculacionesPendientes", vinculaciones
    );

    return new ModelAndView(model, "vinculacionesOrganizacion.html.hbs");
  }

  public ModelAndView aceptarVinculacion(Request request, Response response) {
    UsuarioOrganizacion usuarioOrg = organizacionDeSesion(request);
    Long idVinculacion = Form.of(request).getParamOrError("idVinculacion", "Es necesario indicar un id de vinculación")
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
      "tiposDeConsumo", repoTipoDeConsumo.obtenerTodos()
    );
    return new ModelAndView(model, "cargaDA.html.hbs");
  }

  public ModelAndView cargarDA(Request request, Response response) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();

    List<DatoActividad> DAs = new ArrayList<>();

    if (request.contentType().startsWith("text/csv")) {
      DAs = daDesdeCSV(request);
    } else {
      DAs = daDesdeQueryParams(request);
    }

    final List<DatoActividad> finalDAs = DAs;
    
    withTransaction(() -> {
      organizacion.agregarDatosActividad(finalDAs);
      repoOrganizaciones.actualizar(organizacion);
    });

    response.redirect("/organizaciones/" + organizacion.getId() + "/da");
    return null;
  }

  public ModelAndView reportes(Request request, Response response) {



    if (request.queryParams("evolucion").equals("true")) {

    }




    ImmutableMap<String , Object> model = ImmutableMap.of();
    return new ModelAndView(model, "reportes.html.hbs");
  }

  private List<DatoActividad> daDesdeCSV(Request request) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();

    String CSVString = request.body();
    DatosActividadesParser DAParser = new DatosActividadesParser(
      repoTipoDeConsumo, 
      new LectorDeArchivos(CSVString.getBytes()), 
      1, 
      ';'
    );
    return DAParser.getDatosActividad();
  }

  private List<DatoActividad> daDesdeQueryParams(Request request) {
    Organizacion organizacion = organizacionDeSesion(request).getOrganizacion();

    String DAComoLineaCSV = String.join(";",
      request.queryParams("tipoConsumo"),
      request.queryParams("cantidadConsumo"),
      request.queryParams("periodicidad"),
      request.queryParams("fechaInicial")
    );

    DatosActividadesParser DAParser = new DatosActividadesParser(
      repoTipoDeConsumo, 
      new LectorDeArchivos(DAComoLineaCSV.getBytes()), 
      0, 
      ';'
    );
    return DAParser.getDatosActividad();
  }

}
