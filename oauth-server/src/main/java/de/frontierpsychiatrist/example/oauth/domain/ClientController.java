package de.frontierpsychiatrist.example.oauth.domain;

import de.frontierpsychiatrist.example.oauth.editors.AuthorityPropertyEditor;
import de.frontierpsychiatrist.example.oauth.editors.SplitCollectionEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private JdbcClientDetailsService clientDetailsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // This is mainly needed for the GrantedAuthority array. If we don't use this editor no authorities
        // will get bound to [null] instead of [].
        binder.registerCustomEditor(Collection.class, new SplitCollectionEditor(Set.class, ","));
        // To convert and display roles as strings we use this editor.
        binder.registerCustomEditor(GrantedAuthority.class, new AuthorityPropertyEditor());
    }

    /**
     * Display an edit/create form for a client.
     * @param clientId The id of the client to display. If null a create form will be displayed.
     * @param model The Spring MVC model.
     * @return clients/form view
     */
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
    public String showEditOrAddForm(@RequestParam(value = "client", required = false) String clientId, Model model) {
        ClientDetails clientDetails;
        if(clientId != null) {
            clientDetails = clientDetailsService.loadClientByClientId(clientId);
        } else {
            clientDetails = new BaseClientDetails();
        }
        model.addAttribute("clientDetails", clientDetails);
        return "clients/form";
    }

    /**
     * Create/update a client from the form.
     * @param clientDetails The model to create/update.
     * @param newClient Indicates if this is a new client. If null it's an existing client.
     * @return redirects to the root.
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
    public String editClient(
            @ModelAttribute BaseClientDetails clientDetails,
            @RequestParam(value = "newClient", required = false) String newClient
            ) {
        if (newClient == null) {
            //does not update the secret!
            // TODO: delete tokens and approvals
            clientDetailsService.updateClientDetails(clientDetails);
        } else {
            clientDetailsService.addClientDetails(clientDetails);
        }

        // If the user has entered a secret in the form update it.
        if (!clientDetails.getClientSecret().isEmpty()) {
            clientDetailsService.updateClientSecret(clientDetails.getClientId(), clientDetails.getClientSecret());
        }
        return "redirect:/";
    }
}
