package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    private final JdbcClientDetailsService clientDetailsService;
    private final ApprovalStore approvalStore;
    private final TokenStore tokenStore;

    public IndexController(JdbcClientDetailsService clientDetailsService, ApprovalStore approvalStore, TokenStore tokenStore) {
        this.clientDetailsService = clientDetailsService;
        this.approvalStore = approvalStore;
        this.tokenStore = tokenStore;
    }

    @GetMapping("/")
    public ModelAndView root(Map<String, Object> model, Principal principal) {
        List<Approval> approvals = clientDetailsService.listClientDetails().stream()
                .map(clientDetail -> approvalStore.getApprovals(principal.getName(), clientDetail.getClientId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        model.put("approvals", approvals);
        model.put("clientDetails", clientDetailsService.listClientDetails());
        return new ModelAndView("index", model);
    }

    @PostMapping(value = "/approval/revoke")
    public String revokeApproval(@ModelAttribute Approval approval) {
        approvalStore.revokeApprovals(asList(approval));
        tokenStore
                .findTokensByClientIdAndUserName(approval.getClientId(), approval.getUserId())
                .forEach(tokenStore::removeAccessToken);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
