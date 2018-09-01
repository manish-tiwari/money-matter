package com.poc.moneymatter.services;

import com.poc.moneymatter.dao.entity.User;
import com.poc.moneymatter.dao.repository.UserRepository;
import com.poc.moneymatter.exceptions.UserAlreadyExistsException;
import com.poc.moneymatter.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User findById(UUID id) {
        Optional<User> user=repository.findById(id);
        if(!user.isPresent()) throw new UserNotFoundException("User does not exist !");
        return user.get();
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
