package JMR.AICHAT.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @GetMapping
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/mensajes")
    public String mensajes() {
        return "admin/mensajes";
    }

    @GetMapping("/canchas")
    public String canchas() {
        return "admin/canchas";
    }

    @GetMapping("/reservas")
    public String reservas() {
        return "admin/reservas";
    }
}
