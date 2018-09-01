package com.poc.moneymatter.services;

import com.poc.moneymatter.dao.entity.User;
import com.poc.moneymatter.dao.repository.UserRepository;
import com.poc.moneymatter.exceptions.MoneyMatterUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        if (StringUtils.isEmpty(user.getId())) throw new MoneyMatterUserException("Pease provide User ID !");
        return repository.save(user);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
