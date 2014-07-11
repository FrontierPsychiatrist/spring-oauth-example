package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
@Controller
public class IndexController {

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
        model.put("clientDetails", clientDetailsService.listClientDetails());
        return new ModelAndView("index", model);
    }

    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value = "/approval/revoke", method = RequestMethod.POST)
    public String revokeApproval(@ModelAttribute Approval approval) {
        approvalStore.revokeApprovals(asList(approval));
        tokenStore
                .findTokensByClientIdAndUserName(approval.getClientId(), approval.getUserId())
                .forEach(tokenStore::removeAccessToken);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
}
