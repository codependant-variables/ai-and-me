package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.dao.ICheckInDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock in-memory implementation of ICheckinDAO for testing
 */
public class MockCheckInDAO implements ICheckInDAO {
    private final List<CheckIn> checkIns = new ArrayList<>();
    private static int autoIncrementedId = 1;

    public MockCheckInDAO() {}

    @Override
    public void add(CheckIn checkin) {
        checkin.setId(autoIncrementedId++);
        checkIns.add(checkin);
    }

    @Override
    public void delete(CheckIn checkin) {
        checkIns.remove(checkin);
    }

    @Override
    public void deleteByUserId(int userId) {
        checkIns.removeIf(x -> x.getUserId() == userId);
    }

    @Override
    public List<CheckIn> getAll() {
        return new ArrayList<>(checkIns);
    }

    @Override
    public CheckIn get(int id) {
        return checkIns.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CheckIn> getAllByUserId(int userId) {
        return checkIns.stream()
                .filter(c -> c.getUserId() == userId)
                .toList();
    }
}