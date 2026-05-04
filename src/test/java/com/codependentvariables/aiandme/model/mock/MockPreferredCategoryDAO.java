package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.Category;
import com.codependentvariables.aiandme.model.dao.IPreferredCategoryDAO;
import java.util.ArrayList;
import java.util.List;

public class MockPreferredCategoryDAO implements IPreferredCategoryDAO {
    private record PreferredCategory(int userId, int categoryId) {}

    private final ArrayList<PreferredCategory> preferredCategories = new ArrayList<>();

    @Override
    public void add(int userId, int categoryId) {
        preferredCategories.add(new PreferredCategory(userId, categoryId));
    }

    @Override
    public void delete(int userId, int categoryId) {
        preferredCategories.stream()
                .filter(x -> x.userId == userId && x.categoryId == categoryId)
                .forEach(preferredCategories::remove);
    }

    @Override
    public List<Category> getByUserId(int userId) {
        return preferredCategories.stream()
                .filter(x -> x.userId == userId)
                .map(x -> {
                    Category category = new Category("Not set");
                    category.setId(x.categoryId);
                    return category;
                })
                .toList();
    }

    @Override
    public void deleteByUserId(int userId) {
        preferredCategories.removeIf(x -> x.userId == userId);
    }
}