package TransportationSystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/")
    public String welcome(){
        return "<h1>Welcome to FCI Transportation System!</h1>";
    }
}
