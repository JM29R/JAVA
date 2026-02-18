package JMR.API.infra.Exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestordeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity GestionarError404(){

        return ResponseEntity.notFound().build();

    }

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity GestionarError400(MethodArgumentNotValidException ex){

        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DatosError::new).toList());
   }

   public record DatosError(String campo,String mensaje){
        public DatosError(FieldError Error){
            this(Error.getField(),Error.getDefaultMessage());
        }
   }




}
