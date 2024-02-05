package ku.cs.csProject.controller;

import ku.cs.csProject.model.SignupRequest;
import ku.cs.csProject.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    @Autowired
    private SignupService signupService;

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute SignupRequest user, Model model) {
        if (signupService.isEmailAvailable(user.getEmail())) {
            signupService.createUser(user);
            model.addAttribute("signupSuccess", true);
        }
        else {
            model.addAttribute("signupError", "Email not available");
        }
        return "signup";
    }

}