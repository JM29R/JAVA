package JMR.FOROHUB.infra;


import JMR.FOROHUB.Topicos.DatosListaTopicos;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestordeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionar404(){

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionar400(MethodArgumentNotValidException ex){
    var errores = ex.getFieldErrors();
    return  ResponseEntity.badRequest().body(errores);

    }



}
