package JMR.API.domain.Paciente;



public record DatosListaPaciente(
        long ID,
        String nombre,
        String email,
        String documento
) {
    public DatosListaPaciente(Paciente paciente){
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getDocumento()
        );
    }

}
