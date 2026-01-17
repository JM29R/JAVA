package JMR.SistemaRRHH.Service;
import JMR.SistemaRRHH.Model.Empleado;
import JMR.SistemaRRHH.Repository.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service


public class EmpleadoService implements IEmpleadoService {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Override
    public List<Empleado> ListarEmpleados() {
        return empleadoRepositorio.findAll();
    }

    @Override
    public Empleado BuscarEmpleado(Integer idEmpleado) {
        return empleadoRepositorio.findById(idEmpleado).orElse(null);
    }

    @Override
    public  Empleado GuardarEmpleado(Empleado empleado) {
        return empleadoRepositorio.save(empleado);
    }

    @Override
    public void EliminarEmpleado(Empleado empleado) {
    empleadoRepositorio.delete(empleado);

    }
}
