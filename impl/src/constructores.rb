require_relative '../lib/generador_firmas'

# Estos constructores no están en el código pero se pueden inducir en base al diagrama de clases o leyendo las notas

class Usuario
  attr_accessor :nombre, :apellido, :email, :telefono, :notificadores

  def initialize(nombre, apellido, email, telefono, notificadores)
    self.nombre = nombre
    self.apellido = apellido
    self.email = email
    self.telefono = telefono
    self.notificadores = notificadores
  end
end

class RepositorioProcesosDeFirma
  attr_accessor :procesos

  def initialize
    self.procesos = []
  end
end

class Firmante
  attr_accessor :usuario, :firma

  def initialize(usuario, firma)
    self.usuario = usuario
    self.firma = firma
  end
end

class Colaboracion
  attr_accessor :usuario, :estado

  def initialize(usuario)
    self.usuario = usuario
    self.estado = EstadoColaboracion::DESHABILITADA
  end
end

class Lectura < Colaboracion
end

class Firma < Colaboracion
  attr_accessor :firmador

  def initialize(firmador, usuario)
    super(usuario)
    self.firmador = firmador
  end
end

class Firmador
  attr_accessor :generador

  def initialize
    self.generador = GeneradorDeFirmas.new
  end
end

module EstadoColaboracion
  DESHABILITADA = 0
  PENDIENTE = 1
  COMPLETADA = 2
end
