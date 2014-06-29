package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Moritz Schulze
 */
@Controller
public class LoginController {

    @Autowired
    private JdbcClientDetailsService clientDetailsService;

    @Autowired
    private ApprovalStore approvalStore;

    @RequestMapping("/")
    public ModelAndView root(Map<String, Object> model, Principal principal) {
        List<Approval> approvals = clientDetailsService.listClientDetails().stream()
                .map(clientDetail -> approvalStore.getApprovals(principal.getName(), clientDetail.getClientId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        model.put("approvals", approvals);
        return new ModelAndView("index", model);
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
}
