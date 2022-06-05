package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;



@Controller
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String getAllUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRole());
        return "users";}



    @GetMapping(value = "/admin/add")
    public String newUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("roles", roleService.findAllRole());
        model.addAttribute("user", user);
        return "new";
    }
    @PostMapping (value = "/edit/{id}")
    public String edit( @PathVariable("id") Long id, @ModelAttribute("user") User user,
                        @RequestParam(value="userRolesSelector") String[] selectResult) {
        for(String s : selectResult) {
            user.addRole(roleService.findRoleByName("Role_" + s));
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }


    @PostMapping (value = "/add")
    public String add(@ModelAttribute("emptyUser") User user, @RequestParam(value = "checkedRoles")
                      String[] selectResult) {
     for(String s : selectResult) {
         user.addRole(roleService.findRoleByName("ROLE_" + s));
     }
       userService.save(user);
        return "redirect:/admin";
    }


    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }


    @RequestMapping("/")
    public String pageIndex() {
        return "login";
    }



}
