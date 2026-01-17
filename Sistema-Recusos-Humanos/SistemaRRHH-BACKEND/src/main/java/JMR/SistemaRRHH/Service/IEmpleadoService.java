package JMR.SistemaRRHH.Service;

import JMR.SistemaRRHH.Model.Empleado;

import java.util.List;

public interface IEmpleadoService {
    public List<Empleado> ListarEmpleados();

    public Empleado BuscarEmpleado(Integer idEmpleado);

    public Empleado GuardarEmpleado(Empleado empleado);

    public void EliminarEmpleado(Empleado empleado);



}
