package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;


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
        public String getAllUsers(Model model) {
           model.addAttribute("users", userService.findAll());
           return "users";
        }


        @GetMapping(value = "/admin/add")
        public String newUser(@ModelAttribute("user") User user, Model model) {
            model.addAttribute("roles", roleService.findAllRole());
            return "new";
        }

        @PostMapping(value = "/admin/add")
        public String create(@RequestParam("role") ArrayList<Long> roles,
                             @ModelAttribute("user") User user) {
            user.setRoles(roleService.findByIdRoles(roles));
            userService.save(user);
            return "redirect:/admin";
        }

        @GetMapping(value = "/admin/edit/{id}")
        public String edit(Model model, @PathVariable("id") Long id) {
            model.addAttribute("user", userService.getById(id));
            model.addAttribute("roles", roleService.findAllRole());
            return "edit";
        }

        @PostMapping(value = "/admin/edit")
        public String update(@RequestParam("role") ArrayList<Long> roles,
                             @ModelAttribute("user") User user) {
            user.setRoles(roleService.findByIdRoles(roles));
            userService.save(user);
            return "redirect:/admin";
        }

        @DeleteMapping(value = "/admin/delete/{id}")
        public String delete(@PathVariable("id") int id) {
            userService.deleteById(id);
            return "redirect:/admin";
        }

        @GetMapping("/login")
            public String pageLogin() {
               return "login";
            }

            @GetMapping("/index")
        public String pageIndex() {
            return "index";
            }


}
