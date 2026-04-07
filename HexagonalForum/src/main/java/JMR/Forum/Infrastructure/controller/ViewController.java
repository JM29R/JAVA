package JMR.Forum.Infrastructure.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/view")
public class ViewController {


    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; //templates/home.html
    }

    @GetMapping("/topico/{id}")
    public String topicoPage(@PathVariable Long id) {
        return "topico";
    }

}
