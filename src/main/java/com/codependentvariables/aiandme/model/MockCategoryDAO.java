package com.codependentvariables.aiandme.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock database implementation for storing category data.
 */
public class MockCategoryDAO implements ICategoryDAO {
    private static final ArrayList<Category> categories = new ArrayList<>();
    private static int autoIncrementId = 1;

    public MockCategoryDAO() {
        addCategory(new Category("Arithmetic"));
        addCategory(new Category("Comprehension"));
    }

    @Override
    public void addCategory(Category category) {
        category.setId(autoIncrementId);
        autoIncrementId++;
        categories.add(category);
    }

    @Override
    public void updateCategory(Category category) {
        for(int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId() == category.getId()) {
                categories.set(i, category);
                break;
            }
        }
    }

    @Override
    public void deleteCategory(Category category) {
        categories.remove(category);
    }

    @Override
    public Category get(int id) {
        for(Category category : categories ) {
            if(category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }
}
