package JMR.SistemaRRHH.Controler;


import JMR.SistemaRRHH.Excepcion.RecursoNoEncontrado;
import JMR.SistemaRRHH.Model.Empleado;
import JMR.SistemaRRHH.Service.EmpleadoService;
import JMR.SistemaRRHH.Service.IEmpleadoService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rrhh-app")
@CrossOrigin(value = "http://localhost:3000")

public class EmpleadoControlador {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private IEmpleadoService empleadoServicio;
// http://localhost:8080/rrhh-app/empleados
    @GetMapping("/empleados")
    public List<Empleado> ObtenerEmpleados(){
        var empleados = empleadoServicio.ListarEmpleados();
        empleados.forEach((empleado -> logger.info(empleado.toString())));
        return empleados;

    }

    @PostMapping("/empleados")
    public Empleado AgregarEmpleado(@RequestBody Empleado empleado){
        logger.info("Empleado Agregado: " + empleado);
        return empleadoServicio.GuardarEmpleado(empleado);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> ObtenerEmpleadoById(@PathVariable Integer id){
    Empleado empleado = empleadoServicio.BuscarEmpleado(id);
    if (empleado == null){
        throw new RecursoNoEncontrado("El empleado no existe o no se encontro el empleado id: " + id);
    }
    return ResponseEntity.ok(empleado);

    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> ActualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleadoRecibido){
        Empleado empleado = empleadoServicio.BuscarEmpleado(id);
        if (empleado == null){
            throw new RecursoNoEncontrado("El id recibido no existe o no se encontro el empleado id: " + id);
        }
        empleado.setNombre(empleadoRecibido.getNombre());
        empleado.setDepartamento(empleadoRecibido.getDepartamento());
        empleado.setSalario(empleadoRecibido.getSalario());
        empleadoServicio.GuardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>> EliminarEmpleado(@PathVariable Integer id){
       Empleado empleado = empleadoServicio.BuscarEmpleado(id);
       if (empleado == null){
           throw new RecursoNoEncontrado("empleado no encontrado");
       }
       empleadoServicio.EliminarEmpleado(empleado);
       Map<String, Boolean> map = new HashMap<>();
       map.put("Eliminado", Boolean.TRUE);
       return ResponseEntity.ok(map);

    }

}
