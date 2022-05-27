package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;




    @Controller
    public class AdminController {


        private final UserService userService;

        @Autowired
        public AdminController(UserService userService) {
            this.userService = userService;

        }

        @GetMapping(value = "/admin")
        public String getAllUsers(Model model) {
            model.addAttribute("users", userService.findAll());
            return "users";
        }


        @GetMapping(value = "/admin/add")
        public String newUser(@ModelAttribute("user") User user) {
            return "new";
        }

        @PostMapping(value = "/admin/add")
        public String create(@ModelAttribute("user") User user) {
            userService.save(user);
            return "redirect:/admin";
        }

        @GetMapping(value = "/admin/edit/{id}")
        public String edit(Model model, @PathVariable("id") Long id) {
            model.addAttribute("user", userService.getById(id));
            return "edit";
        }

        @PostMapping(value = "/admin/edit")
        public String update(@ModelAttribute("user") User user) {
            userService.save(user);
            return "redirect:/admin";
        }

        @DeleteMapping(value = "/admin/delete/{id}")
        public String delete(@PathVariable("id") int id) {
            userService.deleteById(id);
            return "redirect:/admin";
        }

}
