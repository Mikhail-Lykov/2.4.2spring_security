package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.service.UserDetailsServiceImpl;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetails;

    @PostMapping(value = "/admin")
    public String create(@ModelAttribute("user") User user,
                         @ModelAttribute("adminRole") String adminRole, ModelMap model){
        if(user.getName() != "" && user.getSurname() != "" && user.getAge() != null &&
                                user.getPassword() != "" && user.getUsername() != "" &&
                                (userDetails.loadUserByUsername(user.getUsername()) == null ||
                                userService.findUser(user.getId()).getUsername().equals(user.getUsername()))){

            user.setRoles(new HashSet<>(Collections.singleton(userService.getRole((long)1))));

            if(adminRole.equals("1")){
                user.getRoles().add(userService.getRole((long)2));
            }

                userService.edit(user);

        }
        else{
            String s = "You entered incorrect data";
            model.addAttribute("message", s);
            return "edit";
        }

        return "redirect:/admin";
    }

    @GetMapping(value = "/admin")
    public String showAllUsers(ModelMap model){
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "/admin/{id}")
    public String editUser(@PathVariable("id") long id, ModelMap model){
        User user = userService.findUser(id);

        Set<String> set = new HashSet<>();
        for(Role u : user.getRoles()){
            set.add(u.getRole());
        }

        Boolean b = false;
        if(set.contains("ROLE_ADMIN")){
            b = true;
        }

        model.addAttribute("user", user);
        model.addAttribute("adminRole", b);
        return "edit";
    }

    @PostMapping(value = "/admin/{id}")
    public String deleteUser(@PathVariable("id") long id){
        userService.delete(id);
        return "redirect: /admin";
    }
}
