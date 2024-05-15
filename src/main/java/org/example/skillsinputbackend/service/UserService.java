package org.example.skillsinputbackend.service;

import org.example.skillsinputbackend.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
}
