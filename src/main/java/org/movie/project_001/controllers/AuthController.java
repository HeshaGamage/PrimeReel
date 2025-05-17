package org.movie.project_001.controllers;

import jakarta.servlet.http.HttpSession;
import org.movie.project_001.models.User;
import org.movie.project_001.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String password) throws IOException {
        userService.signIn(username, email, password);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String logIn(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) throws IOException {

        boolean isAuthenticated = userService.logIn(username, password);

        if (isAuthenticated) {
            User loggedinUser = userService.getUserByUserName(username);

            session.setAttribute("username", loggedinUser.getUsername());
            session.setAttribute("userId", loggedinUser.getUserId());
            session.setAttribute("role", loggedinUser.getRole().name()); // Store role for access control

            if ("ADMIN".equalsIgnoreCase(loggedinUser.getRole().name())) {
                return "redirect:/admin";
            } else {
                return "redirect:/home";
            }

        } else {
            return "redirect:/login?error=true";
        }
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session) throws IOException {
        Object usernameObj = session.getAttribute("username");

        if (usernameObj != null) {
            userService.logOut(usernameObj.toString());
        }

        session.invalidate();
        return "redirect:/login?logout=true";
    }
}
