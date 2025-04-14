package main.service;

import main.models.User;
import main.repository.UserRepository;

import java.util.Map;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean addUser(User user){
        return userRepository.addUser(user);
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public boolean userExists(int id) {
        return userRepository.exists(id);
    }

    public void clearAllUsers() {
        userRepository.clearAll();
    }

    public Map<Integer, User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
