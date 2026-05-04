package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.ICategoryDAO;

import java.util.ArrayList;
import java.util.List;

public class MockCategoryDAO implements ICategoryDAO {
    private final ArrayList<Category> categories = new ArrayList<>();
    private int autoIncrementId = 1;

    public MockCategoryDAO() {
        add(new Category("Arithmetic"));
        add(new Category("Comprehension"));
    }

    @Override
    public void add(Category category) {
        category.setId(autoIncrementId);
        autoIncrementId++;
        categories.add(category);
    }

    @Override
    public void update(Category category) {
        for(int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId() == category.getId()) {
                categories.set(i, category);
                break;
            }
        }
    }

    @Override
    public void delete(Category category) {
        categories.remove(category);
    }

    @Override
    public List<Category> getAll() {
        return new ArrayList<>(categories);
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
}
