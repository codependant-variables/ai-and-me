package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.IUserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MockUserDAO implements IUserDAO {
    private final ArrayList<User> users = new ArrayList<>();
    private int autoIncrementedId = 1;

    public MockUserDAO() {
        add(new User("Amy Adams", "amy.adams@mydomain.gov", "Zl3QG3XY5/Gsus8Ec4WTi6jMcM7EkrCGCqBMgwwYUzg=", "sKH9XkLaT2i1XR687zjlHQ=="));
        add(new User("Bob Builder", "bobthebuilder23@swagmail.net", "Qf15LlrXz/ghNuMGZG3heBeqH3xeuzITnsRhHTDxzR4=", "0akeeTvljQojvcWqb4cg/Q=="));
    }

    @Override
    public void add(User user) {
        user.setId(autoIncrementedId++);
        users.add(user);
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                break;
            }
        }
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }

    @Override
    public List<User> getAll() {
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