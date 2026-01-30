package JMR.API.controller;


import JMR.API.domain.Consulta.DatosDetallesConsulta;
import JMR.API.domain.Consulta.DatosReservaConsulta;
import JMR.API.domain.Consulta.ReservaDeConsultas;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    @Autowired
    private ReservaDeConsultas reserva;

    @PostMapping
    @Transactional
    public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsulta datos){

        reserva.Reservar(datos);


        return ResponseEntity.ok(new DatosDetallesConsulta(null,null,null,null));
    }
}
