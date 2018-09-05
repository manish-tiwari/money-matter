package com.poc.moneymatter.controllers;

import com.poc.moneymatter.dao.entity.User;
import com.poc.moneymatter.exceptions.MoneyMatterUserException;
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

 /*   @GetMapping(value = "/users/email/{email}")
    @ResponseBody
    public User getByEmail(@PathVariable("email") String email) {
        return service.findByEmail(email);
    }

    @GetMapping(value = "/users/id/{id}")
    @ResponseBody
    public User getById(@PathVariable("id") String id) {
        return service.findById(UUID.fromString(id));
    }*/

    @GetMapping(value = "/users")
    @ResponseBody
    public List<User> getAll(@RequestParam(value="email", required=false) String email, @RequestParam(value="id", required=false) String id) {
        if (StringUtils.isEmpty(email) && !StringUtils.isEmpty(id)) return service.findById(UUID.fromString(id)) ;
        else if(!StringUtils.isEmpty(email)) return service.findByEmail(email);
        return service.findAll();
    }

    @PostMapping(value = "/users")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody User user) {
        return service.save(user);
    }

    @DeleteMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") String id) {
        service.delete(UUID.fromString(id));
    }

    @PutMapping(value = "/users")
    @ResponseBody
    public User update(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getId())) throw new MoneyMatterUserException("Pease provide User ID !");
        return service.save(user);
    }
}
