package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.UserPreferredCategory;
import com.codependentvariables.aiandme.model.dao.IUserPreferredCategoryDAO;
import java.util.ArrayList;
import java.util.List;

public class MockUserPreferredCategoryDAO implements IUserPreferredCategoryDAO {
    private final List<UserPreferredCategory> userPreferredCategories = new ArrayList<>();

    @Override
    public void add(UserPreferredCategory userPreferredCategory) {
        userPreferredCategories.add(userPreferredCategory);
    }

    @Override
    public void delete(UserPreferredCategory userPreferredCategory) {
        userPreferredCategories.remove(userPreferredCategory);
    }

    @Override
    public List<UserPreferredCategory> getByUserId(int userId) {
        return userPreferredCategories.stream()
                .filter(x -> x.getUserId() == userId)
                .toList();
    }

    @Override
    public void deleteByUserId(int userId) {
        userPreferredCategories.removeIf(x -> x.getUserId() == userId);
    }
}