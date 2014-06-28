package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Moritz Schulze
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public String root() {
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
}
