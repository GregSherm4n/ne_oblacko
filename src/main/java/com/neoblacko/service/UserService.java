package com.neoblacko.service;

import com.neoblacko.model.User;

import java.util.List;

public interface UserService {

    User addUser(User user);
    User getUser (String userEmail);
    User getUserById (Integer id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(User user);


}
