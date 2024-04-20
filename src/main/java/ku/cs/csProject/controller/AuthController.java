package ku.cs.csProject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ku.cs.csProject.model.SignupRequest;
import ku.cs.csProject.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @GetMapping("/login")
    public String loginView() {
        return "login";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         Authentication auth) {

        if (auth != null) {
            new SecurityContextLogoutHandler()
                    .logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @PostMapping("/lender/register")
    public String lenderRegister(@ModelAttribute SignupRequest user, Principal principal) {
        userDetailsServiceImp.updateRoleUser(user, principal);
        return "redirect:/books/add";
    }

}
