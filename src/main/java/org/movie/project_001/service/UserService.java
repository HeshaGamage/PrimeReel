package org.movie.project_001.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.movie.project_001.models.User;
import org.movie.project_001.utils.UserRoles;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String USERS_FILE = "src/main/resources/data/users.json";
    private final Map<UUID, String> loggedInUsers = new HashMap<>();



    public User getUserByUserName(String username) throws IOException {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    // Sign up new user
    public void signIn(String username, String email, String password) throws IOException {
        List<User> users = getAllUsers();
        boolean exists = users.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(email));

        if (exists) {
            throw new IllegalArgumentException("Username or email already exists!");
        }

        User newUser = new User(username, email, UserRoles.USER, password);
        addUser(newUser);
    }

    // Login user
    public boolean logIn(String username, String password) throws IOException {
        Optional<User> userOpt = getAllUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password))
                .findFirst();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            loggedInUsers.put(user.getUserId(), username);
            return true;
        } else {
            return false;
        }
    }

    // Logout user (safe)
    public void logOut(String username) throws IOException {
        User user = getUserByUserName(username);
        if (user == null) {
            System.out.println("[LOGOUT] User not found: " + username);
            return;
        }

        UUID userId = user.getUserId();
        if (!loggedInUsers.containsKey(userId)) {
            System.out.println("[LOGOUT] User was not logged in: " + username);
            return;
        }

        loggedInUsers.remove(userId);
        System.out.println("[LOGOUT] Successfully logged out: " + username);
    }


    public List<User> getAllUsers() throws IOException {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            mapper.writeValue(file, new ArrayList<User>());
        }
        return Arrays.asList(mapper.readValue(file, User[].class));
    }

    public User getUserById(UUID id) throws IOException {
        if (id == null) return null;

        return getAllUsers().stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addUser(User user) throws IOException {
        List<User> users = new ArrayList<>(getAllUsers());
        users.add(user);
        mapper.writeValue(new File(USERS_FILE), users);
    }

    public void updateUser(UUID id, User updatedUser) throws IOException {
        if (id == null) return;

        List<User> users = new ArrayList<>(getAllUsers());
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(id)) {
                users.set(i, updatedUser);
                break;
            }
        }
        mapper.writeValue(new File(USERS_FILE), users);
    }

    public void deleteUser(UUID id) throws IOException {
        if (id == null) return;

        List<User> users = new ArrayList<>(getAllUsers());
        users.removeIf(user -> user.getUserId().equals(id));
        mapper.writeValue(new File(USERS_FILE), users);
    }
}
