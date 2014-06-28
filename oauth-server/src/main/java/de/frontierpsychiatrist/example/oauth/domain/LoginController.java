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
    @ResponseBody
    public String root() {
        return "OAuth server is running.";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
}
