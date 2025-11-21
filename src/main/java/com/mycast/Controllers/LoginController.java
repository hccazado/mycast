package com.mycast.Controllers;

import com.mycast.Models.User;
import com.mycast.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class LoginController {
    //This controller is responsible for authenticating the user and returning the session.
    private final UserService userService;

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
                            Model model){
        User user = userService.authenticate(username, password); //requiring the user data from the UserService
        if(user == null){
            model.addAttribute("error","Invalid username or password");
            return "login";
        }
        //successful login, adding user to the session and redirecting it to the index page.
        session.setAttribute("user", user);
        return "redirect:/";
    }

    //mapping a Get route to terminate the user session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //removing the user session, and redirecting it to the login page.
        session.invalidate();
        return "redirect:/login";
    }
}
