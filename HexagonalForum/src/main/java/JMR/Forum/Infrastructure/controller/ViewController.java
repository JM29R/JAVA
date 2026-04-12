package JMR.Forum.Infrastructure.controller;

import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.application.service.TopicoService;
import JMR.Forum.domain.model.Topico;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@AllArgsConstructor
@Controller
@RequestMapping("/view")
public class ViewController {

    private TopicoService  topicoService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        List<TopicoResponse> topicos = topicoService.buscarTodos();
        model.addAttribute("topicos", topicos);
        return "home";
    }

    @GetMapping("/topico/{id}")
    public String topicoPage(@PathVariable Long id, Model model) {
        TopicoResponse topico = topicoService.buscarPorId(id);
        model.addAttribute("topico", topico);
        return "topico";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
}

