package com.poc.moneymatter.services;

import com.poc.moneymatter.dao.entity.User;
import com.poc.moneymatter.dao.repository.UserRepository;
import com.poc.moneymatter.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        return repository.save(user);
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
