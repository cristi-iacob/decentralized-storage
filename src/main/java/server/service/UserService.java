package server.service;

import server.model.User;
import server.persistence.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public void login(String email) {
        User user = userRepository.getUserByEmail(email);
        user.setOnline(true);
        userRepository.update(user);
    }

    public void logout(String email) {
        User user = userRepository.getUserByEmail(email);
        user.setOnline(false);
        userRepository.update(user);
    }
}
