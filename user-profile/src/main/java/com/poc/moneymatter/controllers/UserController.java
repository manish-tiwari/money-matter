package com.poc.moneymatter.controllers;

import com.poc.moneymatter.dao.entity.User;
import com.poc.moneymatter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value = "/user/{email}")
    @ResponseBody
    public User getById(@PathVariable("email") String email) {
        return service.findByEmail(email);
    }

    @GetMapping(value = "/user")
    @ResponseBody
    public List<User> getAll() {
        return service.findAll();
    }

    @PostMapping(value = "/user")
    @ResponseBody
    public User save(@RequestBody User user) {
        return service.save(user);
    }

    @DeleteMapping(value = "/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("id") String id) {
        service.delete(UUID.fromString(id));
    }

    @PutMapping(value = "/user")
    @ResponseBody
    public User update(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getId())) throw new IllegalArgumentException("Invalid UserID !");
        return service.save(user);
    }
}
