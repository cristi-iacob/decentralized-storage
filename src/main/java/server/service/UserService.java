package server.service;

import server.exceptions.userexceptions.LogoutException;
import server.exceptions.userexceptions.RegisterException;
import server.exceptions.userexceptions.UserException;
import server.model.User;
import server.persistence.UserRepository;
import server.security.TokenUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private UserRepository userRepository;
    private Map< String, String > tokens;

    public UserService() {
        userRepository = new UserRepository();
        tokens = new HashMap<>();
    }

    public String login(String email, String password) {
        User user = userRepository.getUserByEmailAndPassword(email, password);

        if (user == null) {
            return null;
        }

        if (user.isOnline()) {
            return "-1";
        }

        user.setOnline(true);
        userRepository.update(user);
        String token = TokenUtils.generateRandomToken();
        tokens.put(email, token);
        return token;
    }

    public void logout(String email, String token) throws UserException {
        /*if (!token.equals(tokens.get(email))) {
            throw new BadTokenException();
        }*/

        User user = userRepository.getUserByEmail(email);

        if (!user.isOnline()) {
            throw new LogoutException();
        }

        user.setOnline(false);
        userRepository.update(user);
        tokens.remove(email);
    }

    public void register(String email, String password) throws UserException {
        if (userRepository.getUserByEmail(email) != null) {
            throw new RegisterException();
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        userRepository.add(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllOnlineUsers();
    }

    public List<User> getUsersSortedByRelativeTimezone(int timezone) {
        return userRepository.getUsersSortedByTimezone(timezone);
    }

    public void allonline() {
        userRepository.allonline();
    }

    public void alloffline() {
        userRepository.alloffline();
    }
}
