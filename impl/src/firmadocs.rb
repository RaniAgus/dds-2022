require_relative './constructores'
require 'singleton'

# 1. Es un problema creacional, se resuelve instanciando un proceso de firma

class ProcesoDeFirma
  attr_accessor :colaboraciones, :iniciador, :lectores, :firmantes, :documento, :esta_liberado, :esta_activo, :fecha_generacion

  def initialize(iniciador, documento)
    self.colaboraciones = []
    self.iniciador = iniciador
    self.lectores = []
    self.firmantes = []
    self.documento = documento
    self.esta_liberado = false
    self.esta_activo = true
    self.fecha_generacion = Date.today
  end
end

# 2. Como ocurre al principio, no se puede cambiar y tiene comportamiento distinto dependiendo de uno a otro, me da a
# entender que puedo usar herencia para resolverlo.
# Si es en orden, es posible que necesite que cada colaborador conozca al siguiente para notificarlo (posible iterator)

class ProcesoConOrden < ProcesoDeFirma
end

class ProcesoSinOrden < ProcesoDeFirma
end

# Todas las colaboraciones que se agregan arrancan en estado DESHABILITADA

# 3. El procedimiento en la asignación puede diferir si es en orden o no:

# - Si es en orden, al colaborador anterior se le asigna un siguiente a notificar
class ProcesoConOrden
  def agregar(colaboracion)
    self.colaboraciones.last.siguiente(colaboracion) unless self.colaboraciones.empty?
    super #             ^^^^^^^^^^^^^^^^^^^^^^^^^^^ Podría aprovechar que es un List
  end
end

# - Si es sin orden, directamente lo agregamos a la lista
class ProcesoDeFirma
  def agregar(colaboracion)
    self.colaboraciones.push(colaboracion)
  end
end

# 4. Ambos tipos de procesos tendrán una forma distinta de ser liberados:

# - En el primer caso (en orden), se notifica al primero (y se le habilita)
class ProcesoConOrden
  def liberar
    self.colaboradores.first.habilitar_en(self)
    self.esta_liberado = true # <-- duplicado (template)
  end
end

class Colaboracion
  def habilitar_en(proceso)
    self.usuario.notificarCompartido(proceso)
    self.estado = EstadoColaboracion::PENDIENTE
  end
end

# - En el segundo caso, se hace un foreach para notificar a todos (y habilitar)
class ProcesoSinOrden
  def liberar
    self.colaboradores.each { |it| it.habilitar_en(self) }
    self.esta_liberado = true # <-- duplicado (template)
  end
end

# Ambos tipos de colaboradores tendrán un usuario, que en el caso de la lectura se agregarán como leídos y en el caso de
# la firma usarán sus datos para generarla
class Usuario
  def notificar_compartido(proceso)
    self.notificadores.each { |it| it.notificar_compartido(proceso, self) }
  end
end

# 5. Es simplemente contar con un repositorio de procesos de firma y poder filtrarlos según si son del usuario dueño

class RepositorioProcesosDeFirma
  include Singleton

  def all_by_usuario(usuario)
    self.procesos.filter { |it| it.tiene_usuario?(usuario) }
  end
end

class ProcesoDeFirma
  def tiene_usuario?(usuario)
    self.colaboraciones.any? { |it| it.tiene_usuario?(usuario) }
  end #                             ^^^^^^^^^^^^^^^^ Ok, aunque falta detalle porque depende del estado de colaborador
end

# 6. y 7. Los usuarios lectores se van agregando a medida que completan la acción al igual que las firmas de los
# usuarios firmantes

class ProcesoDeFirma
  def usuarios_firmantes
    self.firmantes.map { |it| it.usuario }
  end
end

# 8. y 9. Ambos me dan a entender la idea de cosificar comportamiento: crear una acción que puede ser ejecutada más
# adelante (en el caso de la fira es el más claro de todos porque se genera en el momento sí o sí)

# 8. Firmas:
class Firma
  def realizar_en(proceso)
    super
    proceso.agregar_firma(self.firmador.generar(self.usuario))
  end
end

class Firmador
  def generar(usuario)
    Firmante.new(
      usuario,
      self.generador.generar_firma(
        usuario.nombre,
        usuario.apellido,
        usuario.email,
        usuario.telefono)
    )
  end
end

class ProcesoDeFirma
  def agregar_firmante(firmante)
    self.firmas.push(firmante)
    self.iniciador.notificar_firma(firmante)
  end
end

# 9. Lecturas:
class Lectura
  def realizar_en(proceso)
    super
    proceso.agregar_lector(self.usuario)
  end
end

class ProcesoDeFirma
  def agregar_lector(usuario)
    self.lectores.push(usuario)
    self.iniciador.notificar_lectura(usuario)
  end
end

class Colaboracion
  def realizar_en(proceso)
    self.estado = EstadoColaboracion::COMPLETADA
    self.siguiente.habilitar_en(proceso) unless self.siguiente.nil?
  end #                                  ^^^^^^ Ojo, hay acoplamiento implícito entre orden y esto
end

# 10. La colaboración puede estar en estado deshabilitada, pendiente o completada. De esta forma, se podrán filtrar
# las que sean pendientes.

class RepositorioProcesosDeFirma
  def all
    self.procesos
  end
end

# Creo un cron en el sistema operativo que cada cierto tiempo ejecute desde ahí un ejecutable ya compilado, con el
# siguiente método main:

class CronJob
  def main_cron
    RepositorioProcesosDeFirma.instance.all.each { |it| it.notificar_pendientes }
  end #                                 ^^^ Falta filtrar si ya están $#@%
end

class ProcesoDeFirma
  def notificar_pendientes
    self.colaboraciones.each { |it| it.notificar_pendiente(self) }
  end
end

class Colaboracion
  def notificar_pendiente(proceso)
    self.usuario.notificar_pendiente(proceso) if self.estado.equal? EstadoColaboracion::PENDIENTE
  end
end

# Y luego, el mismo procedimiento que al notificar un documento compartido

# 11. y 12. Es trivial, lo resuelvo con un booleano

class ProcesoDeFirma
  def anular # podrían re$#%&#@$
    self.esta_activo = false
    self.colaboraciones.each { |it| it.anular }
  end #                 ^^^^ esto es antes que se libere, entonces está deshabilitado

  def cobrable?(mes) # <-- dónde se usa? cómo?
    (esta_liberado or esta_activo) and mes.equal? fecha_generacion.month
  end
end

class Colaboracion
  def anular
    self.estado = EstadoColaboracion::DESHABILITADA
  end
end
