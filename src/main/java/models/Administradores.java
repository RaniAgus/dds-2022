package models;

import java.util.ArrayList;
import java.util.List;

public final class Administradores {
  private static final Administradores instance = new Administradores();
  public List<Administrador> admins;

  public static Administradores getInstance(){ return instance; }

  private Administradores(){
    this.admins = new ArrayList<Administrador>();
  }

  public void agregarAdministrador(Administrador administrador){
    admins.add(administrador);
  }
}
