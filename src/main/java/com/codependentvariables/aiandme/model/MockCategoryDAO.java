package com.codependentvariables.aiandme.model;

import java.util.ArrayList;
import java.util.List;

public class MockCategoryDAO implements ICategoryDAO {
    /**
     * A static list of categories to be used as a mock database.
     */
    public static final ArrayList<Category> categories = new ArrayList<>();
    private static int autoIncrementId = 0;

    public MockCategoryDAO() {
        // Add some initial categories to the mock database
        addCategory(new Category("Mental Math"));
        addCategory(new Category("Puzzle"));
        addCategory(new Category("Literacy"));
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
    public void deleteCategory(Category category) { categories.remove(category); }

    @Override
    public Category getCategory(int id) {
        for(Category category : categories ) {
            if(category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() { return new ArrayList<>(categories); }
}
