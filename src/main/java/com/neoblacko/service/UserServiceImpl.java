package com.neoblacko.service;

import com.neoblacko.model.User;
import com.neoblacko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User getUser(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        var updatedUser = userRepository.saveAndFlush(user);
        return updatedUser;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
