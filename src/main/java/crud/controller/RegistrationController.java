package crud.controller;

import crud.model.User;
import crud.service.UserDetailsServiceImpl;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.HashSet;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetails;

    @GetMapping(value = "/")
    public String printWelcome(){
        return "index";
    }

    @PostMapping(value = "/")
    public String create(@ModelAttribute("user") User user, ModelMap model){
        if(user.getName() != "" && user.getSurname() != "" && user.getAge() != null &&
                                user.getPassword() != "" && user.getUsername() != "" &&
                                userDetails.loadUserByUsername(user.getUsername()) == null){

                user.setRoles(new HashSet<>(Collections.singleton(userService.getRole((long)1))));
                userService.add(user);
                return "redirect:/login";

        }
        else{
            String s = "You entered incorrect data";
            model.addAttribute("message", s);
            return "/new";
        }
    }

    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute("user") User user){
        return "new";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
}
