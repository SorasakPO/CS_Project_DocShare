package ku.cs.csProject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ku.cs.csProject.common.UserRole;
import ku.cs.csProject.model.SignupRequest;
import ku.cs.csProject.service.SignupService;
import ku.cs.csProject.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (userDetails != null) {
        userDetailsServiceImp.updateRoleUser(user, principal);
//        }
//        System.out.println("userDetails: " + userDetails);

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authentication: " + authentication.getAuthorities());

        return "redirect:/books/add";
    }

}
