package JMR.AICHAT.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String chat() {
        return "chat"; // templates/chat.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // templates/login.html
    }

}