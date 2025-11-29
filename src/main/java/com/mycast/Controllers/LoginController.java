package com.mycast.Controllers;

import com.mycast.Models.User;
import com.mycast.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.concurrent.ExecutionException;

@Controller
public class LoginController {
    //This controller is responsible for authenticating the user and returning the session.
    @Autowired
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginController(UserService userService) {
        this.userService = userService;
    }
    //mapping a get route to login page
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    //mapping a POST route to authenticate the user
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) throws ExecutionException, InterruptedException {
        User user = userService.authenticate(username, password); //requiring the user data from the UserService
        if(user == null){
            model.addAttribute("error","Invalid username or password");
            return "login";
        }
        //successful login, adding user to the session and redirecting it to the index page.
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegister(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);
        String hashPwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPwd);
        userService.newUser(user);
        return "register";
    }

    //mapping a Get route to terminate the user session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //removing the user session, and redirecting it to the login page.
        session.invalidate();
        return "redirect:/login";
    }
}
