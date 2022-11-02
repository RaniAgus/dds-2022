package ar.edu.utn.frba.dds.impactoambiental.controllers;

import com.google.common.collect.ImmutableMap;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AgenteSectorialController implements Controller {
  
  public ModelAndView reportesConsumoIndividual(Request request, Response response) {
    SectorTerritorial sector = request.session().attribute("sector");
    
    ImmutableMap<String , Object> model = ImmutableMap.of(
      "sectorTerritorial", sector
    );
    return new ModelAndView(model, "sectorReportesConsumoIndividual.html.hbs");
  }

  public ModelAndView reportesConsumoEvolucion(Request request, Response response) {
    SectorTerritorial sector = request.session().attribute("sector");
    
    ImmutableMap<String , Object> model = ImmutableMap.of(
      "sectorTerritorial", sector
    );
    return new ModelAndView(model, "sectorReportesConsumoEvolucion.html.hbs");
  }


  public ModelAndView reportesOrganizacionIndividual(Request request, Response response) {
    SectorTerritorial sector = request.session().attribute("sector");
    
    ImmutableMap<String , Object> model = ImmutableMap.of(
      "sectorTerritorial", sector
    );
    return new ModelAndView(model, "sectorReportesOrganizacionIndividual.html.hbs");
  }

  public ModelAndView reportesOrganizacionEvolucion(Request request, Response response) {
    SectorTerritorial sector = request.session().attribute("sector");

    ImmutableMap<String , Object> model = ImmutableMap.of(
      "sectorTerritorial", sector
    );
    return new ModelAndView(model, "sectorReportesOrganizacionEvolucion.html.hbs");
  }
}
