package JMR.API.domain.Medico;

public record DatosListaMedico(
        long ID,
  String nombre,
  String email,
  String documento,
  Especialidad especialidad
)
{
    public DatosListaMedico(Medico medico) {
        this(medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getDocumento(),
                medico.getEspecialidad()
        );
    }
}