package com.codependentvariables.aiandme.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MockUserDAO implements IUserDAO {
    /**
     * A static list of users to be used as a mock database.
     */
    public static final ArrayList<User> users = new ArrayList<>();
    private static long autoIncrementedId = 1;

    public MockUserDAO() {
        // Add some initial users to the mock database
        addUser(new User("Amy Adams", "amy.adams@mydomain.gov", "Zl3QG3XY5/Gsus8Ec4WTi6jMcM7EkrCGCqBMgwwYUzg=", "sKH9XkLaT2i1XR687zjlHQ=="));
        addUser(new User("Bob Builder", "bobthebulider23@swagmail.net", "Qf15LlrXz/ghNuMGZG3heBeqH3xeuzITnsRhHTDxzR4=", "0akeeTvljQojvcWqb4cg/Q=="));
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
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public User get(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return user;
            }
        }
        return null;
    }
}