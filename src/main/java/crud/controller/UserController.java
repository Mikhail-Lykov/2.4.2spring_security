package crud.controller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public String userInfo(@AuthenticationPrincipal User user, ModelMap model){
        model.addAttribute("user", userService.findUser(user.getId()));
        return "show";
    }

    @GetMapping(value = "/user/{id}")
    public String showUser(@PathVariable("id") long id, ModelMap model){
        model.addAttribute("user", userService.findUser(id));
        return "show";
    }


}
