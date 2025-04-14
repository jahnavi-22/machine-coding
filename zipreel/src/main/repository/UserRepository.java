package main.repository;

import main.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final HashMap<Integer, User> userStore = new HashMap<>();

    public boolean addUser(User user){
        if(userStore.containsKey(user.getId()))
            return false;
        userStore.put(user.getId(), user);
        return true;
    }

    public User getUserById(int id){
        return userStore.get(id);
    }

    public boolean exists(int id){
        return userStore.containsKey(id);
    }

    public void clearAll(){
        userStore.clear();
    }

    public Map<Integer, User> getAllUsers(){
        return userStore;
    }
}
