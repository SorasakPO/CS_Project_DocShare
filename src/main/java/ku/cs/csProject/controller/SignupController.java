package ku.cs.csProject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ku.cs.csProject.entity.User;
import ku.cs.csProject.model.SignupRequest;
import ku.cs.csProject.repository.UserRepository;
import ku.cs.csProject.service.SignupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import java.security.Principal;

@Controller
public class SignupController {

    @Autowired
    private SignupService signupService;

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute SignupRequest user, @RequestParam("identificationImage") MultipartFile identificationImage, Model model) {
        if (signupService.isEmailAvailable(user.getEmail())) {
            signupService.createUser(user, identificationImage);
            model.addAttribute("signupSuccess", true);
        }
        else {
            model.addAttribute("signupError", "Email not available");
        }
        return "signup";
    }


}