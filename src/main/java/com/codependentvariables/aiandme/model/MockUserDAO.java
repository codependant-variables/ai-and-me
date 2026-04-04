package com.codependentvariables.aiandme.model;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements IUserDAO {
    /**
     * A static list of users to be used as a mock database.
     */
    public static final ArrayList<User> users = new ArrayList<>();
    private static long autoIncrementedId = 1;

    public MockUserDAO() {
        // Add some initial users to the mock database
        addUser(new User("John", "Doe", "johndoe@example.com", "0423423423"));
        addUser(new User("Jane", "Doe", "janedoe@example.com", "0423423424"));
        addUser(new User("Jay", "Doe", "jaydoe@example.com", "0423423425"));
    }

    @Override
    public void addUser(User user) {
        user.setId(autoIncrementedId++);
        users.add(user);
    }

    @Override
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                break;
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }

    @Override
    public User getUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}