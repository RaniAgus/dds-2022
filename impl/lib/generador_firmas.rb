class GeneradorDeFirmas
  def generar_firma(nombre, apellido, email, telefono)
    "#{nombre}#{apellido}#{email}#{telefono}"
  end
end
