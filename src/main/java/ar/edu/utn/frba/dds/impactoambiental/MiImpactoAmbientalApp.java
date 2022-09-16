package ar.edu.utn.frba.dds.impactoambiental;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

public class MiImpactoAmbientalApp {
  public static void main(String[] args) {
    PerThreadEntityManagers.getEntityManager();
  }
}
