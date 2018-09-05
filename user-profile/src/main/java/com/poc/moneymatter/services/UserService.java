package com.poc.moneymatter.services;

import com.poc.moneymatter.dao.entity.User;
import com.poc.moneymatter.dao.repository.UserRepository;
import com.poc.moneymatter.exceptions.UserAlreadyExistsException;
import com.poc.moneymatter.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new UserAlreadyExistsException("User already exists with email ID:'" + user.getEmail() + "'");
        }

    }

    public List<User> findByEmail(String email) {
        List<User> users = new ArrayList<>();
        users.add(repository.findByEmail(email));
        return users;
    }

    public List<User> findById(UUID id) {
        List<User> users = new ArrayList<>();
        Optional<User> user=repository.findById(id);
        if(!user.isPresent()) throw new UserNotFoundException("User does not exist !");
        users.add(user.get());
        return users;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
